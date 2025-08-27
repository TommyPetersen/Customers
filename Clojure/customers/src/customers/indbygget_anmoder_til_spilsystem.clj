(ns customers.indbygget-anmoder-til-spilsystem
  (:require [clojure.string :as str])
)

(defn vaelg-spil []
  (println)
  (println "\t*** Vaelg spil ***")
  (println "\tValg\tBeskrivelse")
  (println "\t----\t-----------")
  (println "\t1\t Traek een vinder (forudvalgt)")
  (println "\t2\t Fire paa stribe")
  (println "\t3\t Infektion")
  (println "\tAndet\t*forudvalgt*")
  (print "\tVaelg spil: ")
  (flush)
  (let [spilvalg (read-line)]
    (case spilvalg
      "1" {
            :spiller1 "lokal-clojureagent://games.draw-a-winner.interaktiv-tekstbaseret-spiller/spiller1"
            :spiller2 "lokal-clojureagent://games.draw-a-winner.ikke-interaktiv-spiller2/spiller2"
	    :dommer "lokal-clojureagent://games.draw-a-winner.ikke-interaktiv-dommer/dommer"
 	  }
      "2" {
            :spiller1 "lokal-clojureagent://games.connect-four.interaktiv-grafikbaseret-spiller/spiller1"
            :spiller2 "lokal-clojureagent://games.connect-four.ikke-interaktiv-spiller-baseret-paa-minimax/spiller2"
	    :dommer "lokal-clojureagent://games.connect-four.ikke-interaktiv-dommer/dommer"
 	  }
      "3" {
            :spiller1 "lokal-clojureagent://games.infection.interaktiv-grafikbaseret-spiller/spiller1"
            :spiller2 "lokal-clojureagent://games.infection.ikke-interaktiv-spiller-baseret-paa-minimax/spiller2"
	    :dommer "lokal-clojureagent://games.infection.ikke-interaktiv-dommer/dommer"
	  }
	  
      {
        :spiller1 "lokal-clojureagent://games.draw-a-winner.interaktiv-tekstbaseret-spiller/spiller1"
        :spiller2 "lokal-clojureagent://games.draw-a-winner.ikke-interaktiv-spiller2/spiller2"
        :dommer "lokal-clojureagent://games.draw-a-winner.ikke-interaktiv-dommer/dommer"
      }
    )
  )
)

(defn foretag-udvaelgelse []
  (let [
         spilvalg (vaelg-spil)
         udvaelgelse {
                       :spiller1 (:spiller1 spilvalg)
                       :spiller2 (:spiller2 spilvalg)
                       :dommer (:dommer spilvalg)
                     }
       ]
       (println)
       udvaelgelse
  )
)

;;; Anmoder ;;;
(defn anmoder [enheds-inddata]
  (let [
  	 foerste-dataelement (first (:data enheds-inddata))
       ]
       (case foerste-dataelement
         "nulstil" {:data ["Ok"]}
         "anmodOmUdvaelgelse" (let [udvaelgelse (foretag-udvaelgelse)]
	                        {:data [(str {:status "udvaelgelseOK" :udvaelgelse udvaelgelse})]}
			      )
         "meddelStatus" (do
                          (println "\n\tSpilstatus: " (nth (:data enheds-inddata) 1) "\n")
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


