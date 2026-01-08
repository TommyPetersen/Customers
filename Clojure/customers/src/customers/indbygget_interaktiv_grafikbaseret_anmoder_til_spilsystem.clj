(ns customers.indbygget-interaktiv-grafikbaseret-anmoder-til-spilsystem
  (:require [clojure.test :refer :all]
            (customers [anmoder-hjaelpefunktioner-aiamg :as anmoder-hjlp-aiamg]
                       [anmoder-hjaelpefunktioner-diverse :as anmoder-hjlp-div])
            [clojure.string :as str]
            [clojure.core.async :refer [go <!! >! timeout]]
  )
  (:import (java.awt.event MouseAdapter WindowAdapter)
           (java.lang System))
)

    ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
    ;;;                             ;;;
    ;;; * Anmoder til Spilsystem  * ;;;
    ;;;                             ;;;
    ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(defn ny-anmoder []
  (let [
         grafikmodul anmoder-hjlp-aiamg/grafikmodul
         vinduesbredde 800
         vindueshoejde 600
         basisramme (anmoder-hjlp-aiamg/beregn-basisramme vinduesbredde vindueshoejde)
         cellegitterkoordinater (anmoder-hjlp-aiamg/beregn-cellegitterkoordinater 7 7 basisramme)
         kantkoordinater (:kantkoordinater cellegitterkoordinater)
         cellekoordinater (:cellekoordinater cellegitterkoordinater)
         braet (atom nil)
         tidsenhed 1000
         tidsgraense 120000
         skal-hent-udvaelgelse-afbrydes (atom false)
         behandl-alle-musehaendelsestyper (atom false)
         kanal-til-hent-udvaelgelse (atom nil)
         braetfigurer {
                        :fire-paa-stribe [{:koordinat [1 5] :symbol "*"} {:koordinat [2 5] :symbol "*"} {:koordinat [3 5] :symbol "*"} {:koordinat [4 5] :symbol "*"}]
                        :infektion [{:koordinat [1 3] :symbol "*"} {:koordinat [2 3] :symbol "*"} {:koordinat [3 3] :symbol "*"}
                                                                   {:koordinat [2 2] :symbol "*"}
                                    {:koordinat [1 1] :symbol "*"} {:koordinat [2 1] :symbol "*"} {:koordinat [3 1] :symbol "*"}]
                      }
         tegn-nedtaelling (fn [
                                samlet-tid-for-anmodning
                                tidsgraense
                              ]
                              (try
                                (dosync
                                  ((@(grafikmodul :funktionalitet) :opdater-tilstand) :samlet-tid-for-anmodning samlet-tid-for-anmodning)
                                  ((@(grafikmodul :funktionalitet) :rens-laerred-tegn-og-vis-alt))
                                )
                                (catch NullPointerException npe
                                  (go (>! @kanal-til-hent-udvaelgelse [-1 -1]))
                                  (throw npe)
                                )
                              )
                          )
         vaelg-spil (fn []
                      (let [
                             resultat-af-udfoer-loekke (anmoder-hjlp-div/udfoer-loekke-via-atom
                                                         (fn [v]
                                                           (tegn-nedtaelling v tidsgraense)
                                                         )
                                                         tidsenhed tidsgraense skal-hent-udvaelgelse-afbrydes
                                                       )
                             fortsaet-nedtaelling-for-spiller (:fortsaet-loekken resultat-af-udfoer-loekke)
                             _ (reset! behandl-alle-musehaendelsestyper true)
                             _ (reset! kanal-til-hent-udvaelgelse (timeout tidsgraense))
                             cellekoordinat (let [indeks (<!! @kanal-til-hent-udvaelgelse)]
                               (if (= indeks nil)
                                 (do
                                   (.dispose (.getScreen (@(grafikmodul :tilstand) :kamera)))
                                   [-1 -1]
                                 )
                                 indeks)
                               )
                             _ (reset! behandl-alle-musehaendelsestyper false)
                             _ ((@(grafikmodul :funktionalitet) :opdater-tilstand) :valgte-celler-indekseret [])
                             _ ((@(grafikmodul :funktionalitet) :opdater-tilstand) :fokuseret-celle-indekseret nil)
                             _ ((@(grafikmodul :funktionalitet) :opdater-tilstand) :fokuseret-celle-rammefarve nil)
                             _ (reset! fortsaet-nedtaelling-for-spiller false)
                             predikat #(and (= (cellekoordinat 0) ((% :koordinat) 0))
                                            (= (cellekoordinat 1) ((% :koordinat) 1)))
                           ]
                           (if (some predikat (braetfigurer :fire-paa-stribe))
                             {
                               :spiller1 "lokal-clojureagent://games.connect-four.interaktiv-grafikbaseret-spiller/spiller1"
                               :spiller2 "lokal-clojureagent://games.connect-four.ikke-interaktiv-spiller-baseret-paa-minimax/spiller2"
                               :dommer "lokal-clojureagent://games.connect-four.ikke-interaktiv-dommer/dommer"
                             }
                             {
                               :spiller1 "lokal-clojureagent://games.infection.interaktiv-grafikbaseret-spiller/spiller1"
                               :spiller2 "lokal-clojureagent://games.infection.ikke-interaktiv-spiller-baseret-paa-minimax/spiller2"
                               :dommer "lokal-clojureagent://games.infection.ikke-interaktiv-dommer/dommer"
                             }
                           )
                       )
                    )
         foretag-udvaelgelse (fn []
                               (let [
                                      spilvalg (vaelg-spil)
                                    ]
                                    {
                                      :spiller1 (:spiller1 spilvalg)
                                      :spiller2 (:spiller2 spilvalg)
                                      :dommer (:dommer spilvalg)
                                    }
                                )
                              )
       ]
       (fn [enheds-inddata]
         (let [foerste-dataelement (first (:data enheds-inddata))]
           (case foerste-dataelement
             "nulstil" (do
                         (reset! braet (anmoder-hjlp-div/opsaet-braet 7 7 braetfigurer))
                         (dosync
                           ((@(grafikmodul :funktionalitet) :fastsaet-tilstand) @braet braetfigurer vinduesbredde vindueshoejde 7 7 [50 10 85 0] 120000)
                           ((@(grafikmodul :funktionalitet) :rens-laerred-tegn-og-vis-alt))
                         )
                         (reset! behandl-alle-musehaendelsestyper true)
                         (reset! kanal-til-hent-udvaelgelse (timeout tidsgraense))
                         (let [
                                fn-behandl-musehaendelse (anmoder-hjlp-aiamg/ny-musehaendelsesbehandler grafikmodul kanal-til-hent-udvaelgelse)
                                fn-behandl-musebevaegelseshaendelse (anmoder-hjlp-aiamg/ny-musebevaegelsehaendelsesbehandler grafikmodul)
                                skaerm (.getScreen (@(grafikmodul :tilstand) :kamera))
                              ]
                              (.addMouseListener skaerm (proxy [MouseAdapter] []
                                                          (mouseClicked [musehaendelse]
                                                            (if @behandl-alle-musehaendelsestyper
                                                              (fn-behandl-musehaendelse musehaendelse)
                                                            )
                                                          )
                                                        )
                              )
                              (.addMouseMotionListener skaerm (proxy [MouseAdapter] []
                                                                (mouseMoved [musebevaegelseshaendelse]
                                                                  (if @behandl-alle-musehaendelsestyper
                                                                    (fn-behandl-musebevaegelseshaendelse musebevaegelseshaendelse)
                                                                  )
                                                                )
                                                              )
                              )
                              (.addWindowListener skaerm (proxy [WindowAdapter] [] (windowClosing [vindueshaendelse] (System/exit 0))))
                         )
                         {:data ["Ok"]}
                       )
             "anmodOmUdvaelgelse" (let [
                                         udvaelgelse (foretag-udvaelgelse)
                                         lokalisering (.getLocationOnScreen (.getScreen ((grafikmodul :tilstand) :kamera)))
                                         vindueslokalisering-x (int (.getX lokalisering))
                                         vindueslokalisering-y (int (.getY lokalisering))
                                         dialogsystem {:vindueslokalisering-x vindueslokalisering-x :vindueslokalisering-y vindueslokalisering-y}
                                       ]
                                       (.turnOff ((grafikmodul :tilstand) :kamera))
                                       {:data [(str {:status "udvaelgelseOK" :udvaelgelse udvaelgelse :dialogsystem dialogsystem})]}
                                  )
             "meddelStatus" {:data ["statusModtaget"]}
             "meddelTidsudloeb" {:data []}

             {:data ["Fejl i data"]}
           )
       )
     )
  )
)

(def anmoder (ny-anmoder))
