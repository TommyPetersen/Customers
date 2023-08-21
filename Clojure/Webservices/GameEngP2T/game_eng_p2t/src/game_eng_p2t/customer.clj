(ns game-eng-p2t.customer
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

(def castings (atom nil))
(def stop-fn (atom nil))

(defn init-customer [req]
  (reset! castings nil)
  {
    :status 200
    :headers {"Content-Type" "application/json"}
    :body (json/write-str {:data ["Ok"]})
  }
)

(defn setup-casting [req]
  (let [
         body (:body req)
         json-map (json/read-str (slurp body))
       ]
       (reset! castings {:player1 (json-map "player1") :player2 (json-map "player2") :arbiter (json-map "arbiter")})
       {
         :status 200
         :headers {"Content-Type" "application/json"}
         :body (json/write-str {:data ["setupOK"]})
       }
  )
)

(defn request-casting [req]
  (if (= @castings nil)
    {
      :status 409
      :headers {"Content-Type" "application/json"}
      :body (json/write-str {:data ["No castings available!"]})
    }
    {
      :status 200
      :headers {"Content-Type" "application/json"}
      :body (json/write-str {:data [(str {:status "castingOK" :castings @castings})]})
    }
  )
)

(defn notify-status [req]
  (let [game-status (:game-status (:params req))]
       {
         :status 200
         :headers {"Content-Type" "application/json"}
         :body (json/write-str {:data ["statusReceived"]})
       }
  )
)

(defn notify-timeout [req]
  {
    :status 200
    :headers {"Content-Type" "application/json"}
    :body (json/write-str {:data ["Oh no, time is up!"]})
  }
)

(defn stop-server [req]
  (@stop-fn)
)

(defroutes app-routes
  (GET "/" [] "This is a web service for a customer requesting a game.")
  (GET "/init-customer" [] init-customer)
  (POST "/setup-casting" [] setup-casting)
  (GET "/request-casting" [] request-casting)
  (GET "/notify-status" [] notify-status)
  (GET "/notify-timeout" [] notify-timeout)
  (GET "/stop-server" [] stop-server)
  
  (route/not-found "Error, page not found!")
)

(defn -main
  "Application main entry."
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "2000"))]
       (reset! stop-fn (server/run-server (wrap-defaults #'app-routes api-defaults) {:port port}))
       (println (str "Running '" (:ns (meta #'-main)) "' as webservice at 'http://127.0.0.1:" port "'"))
  )
)
