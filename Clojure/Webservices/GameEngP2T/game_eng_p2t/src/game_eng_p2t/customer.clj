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

(defn request-casting [req]
  {
    :status 200
    :headers {"Content-Type" "text/json"}
    :body (json/write-str {:data [(str {:status "castingOK" :castings @castings})]})
  }
)

(defn notify-status [req]
  (let [game-status (:game-status (:params req))]
       {
         :status 200
         :headers {"Content-Type" "text/json"}
         :body (json/write-str {:data ["statusReceived"]})
       }
  )
)

(defn notify-timeout [req]
  {
    :status 200
    :headers {"Content-Type" "text/json"}
    :body (json/write-str {:data ["Oh no, time is up!"]})
  }
)

(defroutes app-routes
  (GET "/" [] "This is a web service for a customer requesting a game.")
  (GET "/request-casting" [] request-casting)
  (GET "/notify-status" [] notify-status)
  (GET "/notify-timeout" [] notify-timeout)
  
  (route/not-found "Error, page not found!")
)

(defn -main
  "Application main entry."
  [& args]
  (let [
         json-map (try (json/read-str (slurp "resources/game_setup.json"))
	            (catch java.io.FileNotFoundException e (throw e)))
	 castings-atom-value (reset! castings {:player1 (json-map "player1") :player2 (json-map "player2") :arbiter (json-map "arbiter")})
         port (Integer/parseInt (or (System/getenv "PORT") "2000"))
       ]
       (server/run-server (wrap-defaults #'app-routes site-defaults) {:port port})
       (println (str "Running '" (:ns (meta #'-main)) "' as webservice at 'http://127.0.0.1:" port "'"))
  )
)
