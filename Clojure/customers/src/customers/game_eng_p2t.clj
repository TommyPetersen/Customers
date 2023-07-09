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
  (println "\t1\t[Draw a winner] Non interactive player as \"local-clojure-agent\"")
  (println "\t2\t[Draw a winner] Non interactive player as \"http webservice on localhost port 3201\"")
  (println "\t3\t[Draw a winner] Interactive text player (default)")
  (println "\t4\t[Connect four] Interactive text player")
  (println "\t5\t[Connect four] Interactive gui player")
  (println "\t6\t[Connect four] Non interactive random player as \"local-clojure-agent\"")
  (println "\t7\t[Connect four] Non interactive random player as \"http webservice on localhost port 3003\"")
  (println "\t8\t[Connect four] Non interactive minimax player as \"local-clojure-agent\"")
  (println "\t9\t[Connect four] Non interactive minimax player as \"http webservice on localhost port 3001\"")
  (println "\t10\t[Infection] Interactive text player")
  (println "\t11\t[Infection] Interactive gui player")
  (println "\t12\t[Infection] Non interactive random player as \"local-clojure-agent\"")
  (println "\t13\t[Infection] Non interactive random player as \"http webservice on localhost port 3103\"")
  (println "\t14\t[Infection] Non interactive minimax player as \"local-clojure-agent\"")
  (println "\t15\t[Infection] Non interactive minimax player as \"http webservice on localhost port 3101\"")
  (println "\tOther\t*default*")
  (print "\tChoose player: ")
  (flush)
  (let [player-choice (read-line)]
    (case player-choice
      "1" "local-clojure-agent://games.draw-a-winner.non-interactive-player1/player1"
      "2" "http://localhost:3201"
      "3" "local-clojure-agent://games.draw-a-winner.interactive-text-player/player1"
      "4" "local-clojure-agent://games.connect-four.interactive-text-player/player1"
      "5" "local-clojure-agent://games.connect-four.interactive-gui-player/player1"
      "6" "local-clojure-agent://games.connect-four.non-interactive-random-player/player1"
      "7" "http://localhost:3003"
      "8" "local-clojure-agent://games.connect-four.non-interactive-minimax-player/player1"
      "9" "http://localhost:3001"
      "10" "local-clojure-agent://games.infection.interactive-text-player/player1"
      "11" "local-clojure-agent://games.infection.interactive-gui-player/player1"
      "12" "local-clojure-agent://games.infection.non-interactive-random-player/player1"
      "13" "http://localhost:3103"
      "14" "local-clojure-agent://games.infection.non-interactive-minimax-player/player1"
      "15" "http://localhost:3101"
      
      "local-clojure-agent://games.draw-a-winner.interactive-text-player/player1"
    )
  )
)

(defn choose-player2 []
  (println)
  (println "\t*** Player2 ***")
  (println "\tChoice\tDescription")
  (println "\t------\t-----------")
  (println "\t1\t[Draw a winner] Non interactive player as \"local-clojure-agent\" (default)")
  (println "\t2\t[Draw a winner] Non interactive player as \"http webservice on localhost port 3202\"")
  (println "\t3\t[Draw a winner] Interactive player")
  (println "\t4\t[Connect four] Interactive text player")
  (println "\t5\t[Connect four] Interactive gui player")
  (println "\t6\t[Connect four] Non interactive random player as \"local-clojure-agent\"")
  (println "\t7\t[Connect four] Non interactive random player as \"http webservice on localhost port 3004\"")
  (println "\t8\t[Connect four] Non interactive minimax player as \"local-clojure-agent\"")
  (println "\t9\t[Connect four] Non interactive minimax player as \"http webservice on localhost port 3002\"")
  (println "\t10\t[Infection] Interactive text player")
  (println "\t11\t[Infection] Interactive gui player")
  (println "\t12\t[Infection] Non interactive random player as \"local-clojure-agent\"")
  (println "\t13\t[Infection] Non interactive random player as \"http webservice on localhost port 3104\"")
  (println "\t14\t[Infection] Non interactive minimax player as \"local-clojure-agent\"")
  (println "\t15\t[Infection] Non interactive minimax player as \"http webservice on localhost port 3102\"")
  (println "\tOther\t*default*")
  (print "\tChoose player: ")
  (flush)
  (let [player-choice (read-line)]
    (case player-choice
      "1" "local-clojure-agent://games.draw-a-winner.non-interactive-player2/player2"
      "2" "http://localhost:3202"
      "3" "local-clojure-agent://games.draw-a-winner.interactive-text-player/player2"
      "4" "local-clojure-agent://games.connect-four.interactive-text-player/player2"
      "5" "local-clojure-agent://games.connect-four.interactive-gui-player/player2"
      "6" "local-clojure-agent://games.connect-four.non-interactive-random-player/player2"
      "7" "http://localhost:3004"
      "8" "local-clojure-agent://games.connect-four.non-interactive-minimax-player/player2"
      "9" "http://localhost:3002"
      "10" "local-clojure-agent://games.infection.interactive-text-player/player2"
      "11" "local-clojure-agent://games.infection.interactive-gui-player/player2"
      "12" "local-clojure-agent://games.infection.non-interactive-random-player/player2"
      "13" "http://localhost:3104"
      "14" "local-clojure-agent://games.infection.non-interactive-minimax-player/player2"
      "15" "http://localhost:3102"
      
      "local-clojure-agent://games.draw-a-winner.non-interactive-player2/player2"
    )
  )
)

(defn choose-arbiter []
  (println)
  (println "\t*** Arbiter ***")
  (println "\tChoice\tDescription")
  (println "\t------\t-----------")
  (println "\t1\t[Draw a winner] Non interactive arbiter as \"local-clojure-agent\" (default)")
  (println "\t2\t[Draw a winner] Non interactive arbiter as \"http webservice on localhost port 3200\"")
  (println "\t3\t[Connect four] Non interactive arbiter as \"local-clojure-agent\"")
  (println "\t4\t[Connect four] Non interactive arbiter as \"http webservice on localhost port 3000\"")
  (println "\t5\t[Infection] Non interactive arbiter as \"local-clojure-agent\"")
  (println "\t6\t[Infection] Non interactive arbiter as \"http webservice on localhost port 3100\"")
  (println "\tOther\t*default*")
  (print "\tChoose arbiter: ")
  (flush)
  (let [arbiter-choice (read-line)]
    (case arbiter-choice
      "1" "local-clojure-agent://games.draw-a-winner.non-interactive-arbiter/arbiter"
      "2" "http://localhost:3200"
      "3" "local-clojure-agent://games.connect-four.non-interactive-arbiter/arbiter"
      "4" "http://localhost:3000"
      "5" "local-clojure-agent://games.infection.non-interactive-arbiter/arbiter"
      "6" "http://localhost:3100"
      
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

