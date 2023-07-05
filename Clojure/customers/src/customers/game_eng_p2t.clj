(ns customers.game-eng-p2t
  (:require [clojure.test :refer :all]
    	    [clojure.string :as str]
  )
)


(defn choose-player1 []
  (println)
  (println "\t*** Player1 ***")
  (println "\tChoice\tDescription")
  (println "\t------\t-----------")
  (println "\t1\t[Draw a winner] Non interactive player")
  (println "\t2\t[Draw a winner] Interactive text player (default)")
  (println "\t3\t[Connect four] Interactive text player")
  (println "\t4\t[Connect four] Interactive gui player")
  (println "\t5\t[Connect four] Non interactive random player")
  (println "\t6\t[Connect four] Non interactive minimax player")
  (println "\t7\t[Infection] Interactive text player")
  (println "\t8\t[Infection] Interactive gui player")
  (println "\t9\t[Infection] Non interactive random player")
  (println "\t10\t[Infection] Non interactive minimax player")
  (println "\tOther\t*default*")
  (print "\tChoose player: ")
  (flush)
  (let [player-choice (read-line)]
    (case player-choice
      "1" "local-clojure-agent://games.draw-a-winner.non-interactive-player1/player1"
      "2" "local-clojure-agent://games.draw-a-winner.interactive-text-player/player1"
      "3" "local-clojure-agent://games.connect-four.interactive-text-player/player1"
      "4" "local-clojure-agent://games.connect-four.interactive-gui-player/player1"
      "5" "local-clojure-agent://games.connect-four.non-interactive-random-player/player1"
      "6" "local-clojure-agent://games.connect-four.non-interactive-minimax-player/player1"
      "7" "local-clojure-agent://games.infection.interactive-text-player/player1"
      "8" "local-clojure-agent://games.infection.interactive-gui-player/player1"
      "9" "local-clojure-agent://games.infection.non-interactive-random-player/player1"
      "10" "local-clojure-agent://games.infection.non-interactive-minimax-player/player1"
      "local-clojure-agent://games.draw-a-winner.interactive-text-player/player1"
    )
  )
)

(defn choose-player2 []
  (println)
  (println "\t*** Player2 ***")
  (println "\tChoice\tDescription")
  (println "\t------\t-----------")
  (println "\t1\t[Draw a winner] Non interactive player (default)")
  (println "\t2\t[Draw a winner] Interactive player")
  (println "\t3\t[Connect four] Interactive text player")
  (println "\t4\t[Connect four] Interactive gui player")
  (println "\t5\t[Connect four] Non interactive random player")
  (println "\t6\t[Connect four] Non interactive minimax player")
  (println "\t7\t[Infection] Interactive text player")
  (println "\t8\t[Infection] Interactive gui player")
  (println "\t9\t[Infection] Non interactive random player")
  (println "\t10\t[Infection] Non interactive minimax player")
  (println "\tOther\t*default*")
  (print "\tChoose player: ")
  (flush)
  (let [player-choice (read-line)]
    (case player-choice
      "1" "local-clojure-agent://games.draw-a-winner.non-interactive-player2/player2"
      "2" "local-clojure-agent://games.draw-a-winner.interactive-text-player/player2"
      "3" "local-clojure-agent://games.connect-four.interactive-text-player/player2"
      "4" "local-clojure-agent://games.connect-four.interactive-gui-player/player2"
      "5" "local-clojure-agent://games.connect-four.non-interactive-random-player/player2"
      "6" "local-clojure-agent://games.connect-four.non-interactive-minimax-player/player2"
      "7" "local-clojure-agent://games.infection.interactive-text-player/player2"
      "8" "local-clojure-agent://games.infection.interactive-gui-player/player2"
      "9" "local-clojure-agent://games.infection.non-interactive-random-player/player2"
      "10" "local-clojure-agent://games.infection.non-interactive-minimax-player/player2"
      "local-clojure-agent://games.draw-a-winner.non-interactive-player2/player2"
    )
  )
)

(defn choose-arbiter []
  (println)
  (println "\t*** Arbiter ***")
  (println "\tChoice\tDescription")
  (println "\t------\t-----------")
  (println "\t1\t[Draw a winner] Non interactive arbiter (default)")
  (println "\t2\t[Connect four] Non interactive arbiter as \"local-clojure-agent\"")
  (println "\t3\t[Connect four] Non interactive arbiter as \"http webservice on localhost port 3000\"")
  (println "\t4\t[Infection] Non interactive arbiter")
  (println "\tOther\t*default*")
  (print "\tChoose arbiter: ")
  (flush)
  (let [arbiter-choice (read-line)]
    (case arbiter-choice
      "1" "local-clojure-agent://games.draw-a-winner.non-interactive-arbiter/arbiter"
      "2" "local-clojure-agent://games.connect-four.non-interactive-arbiter/arbiter"
      "3" "https://localhost:3000"
      "4" "local-clojure-agent://games.infection.non-interactive-arbiter/arbiter"
      "local-clojure-agent://games.draw-a-winner.non-interactive-arbiter/arbiter"
    )
  )
)

(defn cast-actors []
  (let [castings {
                   :player1 (choose-player1)
                   :player2 (choose-player2)
                   :arbiter (choose-arbiter)
                 }
       ]
       (println)
       castings
  )
)

;;; customer ;;;

(defn customer [unit-input]
  (let [
  	 first-data-element (first (:data unit-input))
       ]
       (case first-data-element
         "request-casting" (let [castings (cast-actors)]
	                     {:data [(str {:status "castingOK" :castings castings})]}
			   )
         "notify-status" (do
                           (println "\n\tGame status: " (nth (:data unit-input) 1) "\n")
			   {:data ["statusReceived"]}
			 )
	 "notify-timeout" (do
	                    (println "\n\t*** Timeout occured ***\n")
	                    {:data []}
			  )

	 {:data ["Error in data"]}
       )
  )
)


;;; TESTS ;;;

(deftest unit-test
  (testing "Unit"
    (let [a 1]
      (is (= a 1))
    )
  )
)

