(ns spilsystem.anmoder
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
	    [compojure.route :as route]
	    [ring.middleware.defaults :refer :all]
	    [clojure.pprint :as pp]
	    [clojure.string :as str]
	    [clojure.data.json :as json]
  )
  (:gen-class)
)

(def udvaelgelse (atom nil))
(def stop-fn (atom nil))

(defn initialiser-anmoder [req]
  (reset! udvaelgelse nil)
  {
    :status 200
    :headers {"Content-Type" "application/json"}
    :body (json/write-str {:data ["Ok"]})
  }
)

(defn fastsaet-udvaelgelse [req]
  (let [
         body (:body req)
         json-map (json/read-str (slurp body))
       ]
       (reset! udvaelgelse {:spiller1 (json-map "spiller1") :spiller2 (json-map "spiller2") :dommer (json-map "dommer")})
       {
         :status 200
         :headers {"Content-Type" "application/json"}
         :body (json/write-str {:data ["fastsaettelseOk"]})
       }
  )
)

(defn anmod-om-udvaelgelse [req]
  (if (= @udvaelgelse nil)
    {
      :status 409
      :headers {"Content-Type" "application/json"}
      :body (json/write-str {:data ["Ingen udvaelgelse tilgaengelig!"]})
    }
    {
      :status 200
      :headers {"Content-Type" "application/json"}
      :body (json/write-str {:data [(str {:status "udvaelgelseOK" :udvaelgelse @udvaelgelse})]})
    }
  )
)

(defn meddel-status [req]
    {
      :status 200
      :headers {"Content-Type" "application/json"}
      :body (json/write-str {:data ["statusModtaget"]})
    }
)

(defn meddel-tidsudloeb [req]
  {
    :status 200
    :headers {"Content-Type" "application/json"}
    :body (json/write-str {:data ["Tiden er udloebet!"]})
  }
)

(defn stop-tjenesten [req]
  (@stop-fn)
)

(defroutes app-routes
  (GET "/" [] "Dette er en webtjeneste til brug for en spilanmoder.")
  (GET "/initialiserAnmoder" [] initialiser-anmoder)
  (POST "/fastsaetudvaelgelse" [] fastsaet-udvaelgelse)
  (GET "/anmodOmUdvaelgelse" [] anmod-om-udvaelgelse)
  (GET "/meddelStatus" [] meddel-status)
  (GET "/meddel-tidsudloeb" [] meddel-tidsudloeb)
  (GET "/stopTjenesten" [] stop-tjenesten)
  
  (route/not-found "Fejl, den anmodede side findes ikke!")
)

(defn -main
  "Tjenestens startfunktion."
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "2000"))]
       (reset! stop-fn (server/run-server (wrap-defaults #'app-routes api-defaults) {:port port}))
       (println (str "Koerer '" (:ns (meta #'-main)) "' som webtjeneste paa 'http://127.0.0.1:" port "'"))
  )
)
