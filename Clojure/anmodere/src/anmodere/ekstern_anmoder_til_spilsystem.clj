(ns anmodere.ekstern-anmoder-til-spilsystem
  (:require [clojure.string :as str])
)

(defn choose-player1 []
  (println)
  (println "\t*** Spiller1 ***")
  (println "\tValg\tBeskrivelse")
  (println "\t----\t-----------")
  (println "\t1\t[Traek en vinder] Ikke-interaktiv spiller som \"lokal-clojureagent\"")
  (println "\t2\t[Traek en vinder] Interaktiv tekstbaseret spiller (forudvalgt)")
  (println "\t\t")
  (println "\t3\t[Fire paa stribe] Interaktiv tekstbaseret spiller")
  (println "\t4\t[Fire paa stribe] Interaktiv grafikbaseret spiller")
  (println "\t5\t[Fire paa stribe] Ikke-interaktiv spiller baseret paa tilfaeldige traek som \"lokal-clojureagent\"")
  (println "\t6\t[Fire paa stribe] Ikke-interaktiv spiller baseret paa minimax som \"lokal-clojureagent\"")
  (println "\t\t")
  (println "\t7\t[Infektion] Interaktiv tekstbaseret spiller")
  (println "\t8\t[Infektion] Interaktiv grafikbaseret spiller")
  (println "\t9\t[Infektion] Ikke-interaktiv spiller baseret paa tilfaeldige traek som \"lokal-clojureagent\"")
  (println "\t10\t[Infektion] Ikke-interaktiv spiller baseret paa minimax som \"lokal-clojureagent\"")
  (println "\tAndet\t*forudvalgt*")
  (print "\tVaelg spiller: ")
  (flush)
  (let [player-choice (read-line)]
    (case player-choice
      "1" "lokal-clojureagent://games.draw-a-winner.ikke-interaktiv-spiller1/spiller1"
      "2" "lokal-clojureagent://games.draw-a-winner.interaktiv-tekstbaseret-spiller/spiller1"
      
      "3" "lokal-clojureagent://games.connect-four.interaktiv-tekstbaseret-spiller/spiller1"
      "4" "lokal-clojureagent://games.connect-four.interaktiv-grafikbaseret-spiller/spiller1"
      "5" "lokal-clojureagent://games.connect-four.ikke-interaktiv-spiller-baseret-paa-tilfaeldige-traek/spiller1"
      "6" "lokal-clojureagent://games.connect-four.ikke-interaktiv-spiller-baseret-paa-minimax/spiller1"
      
      "7" "lokal-clojureagent://games.infection.interaktiv-tekstbaseret-spiller/spiller1"
      "8" "lokal-clojureagent://games.infection.interaktiv-grafikbaseret-spiller/spiller1"
      "9" "lokal-clojureagent://games.infection.ikke-interaktiv-spiller-baseret-paa-tilfaeldige-traek/spiller1"
      "10" "lokal-clojureagent://games.infection.ikke-interaktiv-spiller-baseret-paa-minimax/spiller1"
      
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
  (println "\t2\t[Traek en vinder] Interaktiv tekstbaseret spiller")
  (println "\t\t")
  (println "\t3\t[Fire paa stribe] Interaktiv tekstbaseret spiller")
  (println "\t4\t[Fire paa stribe] Interaktiv grafikbaseret spiller")
  (println "\t5\t[Fire paa stribe] Ikke-interaktiv spiller baseret paa tilfaeldige traek som \"lokal-clojureagent\"")
  (println "\t6\t[Fire paa stribe] Ikke-interaktiv spiller baseret paa minimax som \"lokal-clojureagent\"")
  (println "\t\t")
  (println "\t7\t[Infektion] Interaktiv tekstbaseret spiller")
  (println "\t8\t[Infektion] Interaktiv grafikbaseret spiller")
  (println "\t9\t[Infektion] Ikke-interaktiv spiller baseret paa tilfaeldige traek som \"lokal-clojureagent\"")
  (println "\t10\t[Infektion] Ikke-interaktiv spiller baseret paa minimax som \"lokal-clojureagent\"")
  (println "\tAndet\t*forudvalgt*")
  (print "\tVaelg spiller: ")
  (flush)
  (let [player-choice (read-line)]
    (case player-choice
      "1" "lokal-clojureagent://games.draw-a-winner.ikke-interaktiv-spiller2/spiller2"
      "2" "lokal-clojureagent://games.draw-a-winner.interaktiv-tekstbaseret-spiller/spiller2"
      
      "3" "lokal-clojureagent://games.connect-four.interaktiv-tekstbaseret-spiller/spiller2"
      "4" "lokal-clojureagent://games.connect-four.interaktiv-grafikbaseret-spiller/spiller2"
      "5" "lokal-clojureagent://games.connect-four.ikke-interaktiv-spiller-baseret-paa-tilfaeldige-traek/spiller2"
      "6" "lokal-clojureagent://games.connect-four.ikke-interaktiv-spiller-baseret-paa-minimax/spiller2"
      
      "7" "lokal-clojureagent://games.infection.interaktiv-tekstbaseret-spiller/spiller2"
      "8" "lokal-clojureagent://games.infection.interaktiv-grafikbaseret-spiller/spiller2"
      "9" "lokal-clojureagent://games.infection.ikke-interaktiv-spiller-baseret-paa-tilfaeldige-traek/spiller2"
      "10" "lokal-clojureagent://games.infection.ikke-interaktiv-spiller-baseret-paa-minimax/spiller2"
      
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
  (println "\t\t")
  (println "\t2\t[Fire paa stribe] Ikke-interaktiv dommer som \"lokal-clojureagent\"")
  (println "\t\t")
  (println "\t3\t[Infektion] Ikke-interaktiv dommer som \"lokal-clojureagent\"")
  (println "\tAndet\t*forudvalgt*")
  (print "\tVaelg dommer: ")
  (flush)
  (let [arbiter-choice (read-line)]
    (case arbiter-choice
      "1" "lokal-clojureagent://games.draw-a-winner.ikke-interaktiv-dommer/dommer"
      
      "2" "lokal-clojureagent://games.connect-four.ikke-interaktiv-dommer/dommer"
      
      "3" "lokal-clojureagent://games.infection.ikke-interaktiv-dommer/dommer"
      
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
	 "meddelTidsudloeb" (do
	                    (println "\n\t*** Tiden er udloebet ***\n")
	                    {:data []}
			  )

	 {:data ["Fejl i data"]}
       )
  )
)


