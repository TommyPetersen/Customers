(ns customers.anmoder-til-spilsystem
  (:require [clojure.test :refer :all]
    	    [clojure.string :as str]
  )
)

(defn choose-player1 []
  (println)
  (println "\t*** Spiller1 ***")
  (println "\tValg\tBeskrivelse")
  (println "\t----\t-----------")
  (println "\t1\t[Traek en vinder] Ikke-interaktiv spiller som \"lokal-clojureagent\"")
  (println "\t2\t[Traek en vinder] Ikke-interaktiv spiller som \"http webservice paa localhost port 3201\"")
  (println "\t3\t[Traek en vinder] Interaktiv tekstbaseret spiller (forudvalgt)")
  (println "\t\t")
  (println "\t4\t[Fire paa stribe] Interaktiv tekstbaseret spiller")
  (println "\t5\t[Fire paa stribe] Interaktiv grafikbaseret spiller")
  (println "\t6\t[Fire paa stribe] Ikke-interaktiv spiller baseret paa tilfaeldige traek som \"lokal-clojureagent\"")
  (println "\t7\t[Fire paa stribe] Ikke-interaktiv spiller baseret paa tilfaeldige traek som \"http webservice paa localhost port 3003\"")
  (println "\t8\t[Fire paa stribe] Ikke-interaktiv spiller baseret paa minimax som \"lokal-clojureagent\"")
  (println "\t9\t[Fire paa stribe] Ikke-interaktiv spiller baseret paa minimax som \"http webservice paa localhost port 3001\"")
  (println "\t\t")
  (println "\t10\t[Infektion] Interaktiv tekstbaseret spiller")
  (println "\t11\t[Infektion] Interaktiv grafikbaseret spiller")
  (println "\t12\t[Infektion] Ikke-interaktiv spiller baseret paa tilfaeldige traek som \"lokal-clojureagent\"")
  (println "\t13\t[Infektion] Ikke-interaktiv spiller baseret paa tilfaeldige traek som \"http webservice paa localhost port 3103\"")
  (println "\t14\t[Infektion] Ikke-interaktiv spiller baseret paa minimax som \"lokal-clojureagent\"")
  (println "\t15\t[Infektion] Ikke-interaktiv spiller baseret paa minimax som \"http webservice paa localhost port 3101\"")
  (println "\tAndet\t*forudvalgt*")
  (print "\tVaelg spiller: ")
  (flush)
  (let [player-choice (read-line)]
    (case player-choice
      "1" "lokal-clojureagent://games.draw-a-winner.ikke-interaktiv-spiller1/spiller1"
      "2" "http://localhost:3201"
      "3" "lokal-clojureagent://games.draw-a-winner.interaktiv-tekstbaseret-spiller/spiller1"
      
      "4" "lokal-clojureagent://games.connect-four.interaktiv-tekstbaseret-spiller/spiller1"
      "5" "lokal-clojureagent://games.connect-four.interaktiv-grafikbaseret-spiller/spiller1"
      "6" "lokal-clojureagent://games.connect-four.ikke-interaktiv-spiller-baseret-paa-tilfaeldige-traek/spiller1"
      "7" "http://localhost:3003"
      "8" "lokal-clojureagent://games.connect-four.ikke-interaktiv-spiller-baseret-paa-minimax/spiller1"
      "9" "http://localhost:3001"
      
      "10" "lokal-clojureagent://games.infection.interaktiv-tekstbaseret-spiller/spiller1"
      "11" "lokal-clojureagent://games.infection.interaktiv-grafikbaseret-spiller/spiller1"
      "12" "lokal-clojureagent://games.infection.ikke-interaktiv-spiller-baseret-paa-tilfaeldige-traek/spiller1"
      "13" "http://localhost:3103"
      "14" "lokal-clojureagent://games.infection.ikke-interaktiv-spiller-baseret-paa-minimax/spiller1"
      "15" "http://localhost:3101"
      
      "lokal-clojureagent://games.draw-a-winner.interaktiv-tekstbaseret-spiller/spiller1"
    )
  )
)

(defn choose-player2 []
  (println)
  (println "\t*** Spiller2 ***")
  (println "\tValg\tBeskrivelse")
  (println "\t----\t-----------")
  (println "\t1\t[Traek en vinder] Ikke-interaktiv spiller som \"lokal-clojureagent\" (forudvalgt)")
  (println "\t2\t[Traek en vinder] Ikke-interaktiv spiller som \"http webservice paa localhost port 3202\"")
  (println "\t3\t[Traek en vinder] Interaktiv tekstbaseret spiller")
  (println "\t\t")
  (println "\t4\t[Fire paa stribe] Interaktiv tekstbaseret spiller")
  (println "\t5\t[Fire paa stribe] Interaktiv grafikbaseret spiller")
  (println "\t6\t[Fire paa stribe] Ikke-interaktiv spiller baseret paa tilfaeldige traek som \"lokal-clojureagent\"")
  (println "\t7\t[Fire paa stribe] Ikke-interaktiv spiller baseret paa tilfaeldige traek som \"http webservice paa localhost port 3004\"")
  (println "\t8\t[Fire paa stribe] Ikke-interaktiv spiller baseret paa minimax som \"lokal-clojureagent\"")
  (println "\t9\t[Fire paa stribe] Ikke-interaktiv spiller baseret paa minimax som \"http webservice paa localhost port 3002\"")
  (println "\t\t")
  (println "\t10\t[Infektion] Interaktiv tekstbaseret spiller")
  (println "\t11\t[Infektion] Interaktiv grafikbaseret spiller")
  (println "\t12\t[Infektion] Ikke-interaktiv spiller baseret paa tilfaeldige traek som \"lokal-clojureagent\"")
  (println "\t13\t[Infektion] Ikke-interaktiv spiller baseret paa tilfaeldige traek som \"http webservice paa localhost port 3104\"")
  (println "\t14\t[Infektion] Ikke-interaktiv spiller baseret paa minimax som \"lokal-clojureagent\"")
  (println "\t15\t[Infektion] Ikke-interaktiv spiller baseret paa minimax som \"http webservice paa localhost port 3102\"")
  (println "\tAndet\t*forudvalgt*")
  (print "\tVaelg spiller: ")
  (flush)
  (let [player-choice (read-line)]
    (case player-choice
      "1" "lokal-clojureagent://games.draw-a-winner.ikke-interaktiv-spiller2/spiller2"
      "2" "http://localhost:3202"
      "3" "lokal-clojureagent://games.draw-a-winner.interaktiv-tekstbaseret-spiller/spiller2"
      
      "4" "lokal-clojureagent://games.connect-four.interaktiv-tekstbaseret-spiller/spiller2"
      "5" "lokal-clojureagent://games.connect-four.interaktiv-grafikbaseret-spiller/spiller2"
      "6" "lokal-clojureagent://games.connect-four.ikke-interaktiv-spiller-baseret-paa-tilfaeldige-traek/spiller2"
      "7" "http://localhost:3004"
      "8" "lokal-clojureagent://games.connect-four.ikke-interaktiv-spiller-baseret-paa-minimax/spiller2"
      "9" "http://localhost:3002"
      
      "10" "lokal-clojureagent://games.infection.interaktiv-tekstbaseret-spiller/spiller2"
      "11" "lokal-clojureagent://games.infection.interaktiv-grafikbaseret-spiller/spiller2"
      "12" "lokal-clojureagent://games.infection.ikke-interaktiv-spiller-baseret-paa-tilfaeldige-traek/spiller2"
      "13" "http://localhost:3104"
      "14" "lokal-clojureagent://games.infection.ikke-interaktiv-spiller-baseret-paa-minimax/spiller2"
      "15" "http://localhost:3102"
      
      "lokal-clojureagent://games.draw-a-winner.ikke-interaktiv-spiller2/spiller2"
    )
  )
)

(defn choose-arbiter []
  (println)
  (println "\t*** Dommer ***")
  (println "\tValg\tBeskrivelse")
  (println "\t----\t-----------")
  (println "\t1\t[Traek en vinder] Ikke-interaktiv dommer som \"lokal-clojureagent\" (forudvalgt)")
  (println "\t2\t[Traek en vinder] Ikke-interaktiv dommer som \"http webservice paa localhost port 3200\"")
  (println "\t\t")
  (println "\t3\t[Fire paa stribe] Ikke-interaktiv dommer som \"lokal-clojureagent\"")
  (println "\t4\t[Fire paa stribe] Ikke-interaktiv dommer som \"http webservice paa localhost port 3000\"")
  (println "\t\t")
  (println "\t5\t[Infektion] Ikke-interaktiv dommer som \"lokal-clojureagent\"")
  (println "\t6\t[Infektion] Ikke-interaktiv dommer som \"http webservice paa localhost port 3100\"")
  (println "\tAndet\t*forudvalgt*")
  (print "\tVaelg dommer: ")
  (flush)
  (let [arbiter-choice (read-line)]
    (case arbiter-choice
      "1" "lokal-clojureagent://games.draw-a-winner.ikke-interaktiv-dommer/dommer"
      "2" "http://localhost:3200"
      
      "3" "lokal-clojureagent://games.connect-four.ikke-interaktiv-dommer/dommer"
      "4" "http://localhost:3000"
      
      "5" "lokal-clojureagent://games.infection.ikke-interaktiv-dommer/dommer"
      "6" "http://localhost:3100"
      
      "lokal-clojureagent://games.draw-a-winner.ikke-interaktiv-dommer/dommer"
    )
  )
)

(defn cast-actors []
  (let [castings {
                   :spiller1 (choose-player1)
                   :spiller2 (choose-player2)
                   :dommer (choose-arbiter)
                 }
       ]
       (println)
       castings
  )
)

;;; Anmoder ;;;
(defn anmoder [unit-input]
  (let [
  	 first-data-element (first (:data unit-input))
       ]
       (case first-data-element
         "nulstil" {:data ["Ok"]}
         "anmodOmUdvaelgelse" (let [castings (cast-actors)]
	                        {:data [(str {:status "udvaelgelseOK" :udvaelgelse castings})]}
			      )
         "meddelStatus" (do
                          (println "\n\tSpilstatus: " (nth (:data unit-input) 1) "\n")
			  {:data ["statusModtaget"]}
			)
	 ;;; Foelgende kommer fra "leipstral.hjaelpefunktioner: ;;;
	 "notify-timeout" (do
	                    (println "\n\t*** Tiden er udloebet ***\n")
	                    {:data []}
			  )

	 {:data ["Fejl i data"]}
       )
  )
)


