(ns customers.anmoder-hjaelpefunktioner-diverse
  (:require [clojure.core.async :refer [go]])
)

(defn opdater-celle [braet [j i] symbol]
  (assoc braet j (assoc (braet j) i symbol))
)

(defn opdater-celler [braet cells]
  (loop [
          var-braet braet
          var-celler cells
        ]
        (if (= (count var-celler) 0)
          var-braet
          (recur
            (opdater-celle var-braet (:koordinat (first var-celler)) (:symbol (first var-celler)))
            (rest var-celler)
          )
        )
  )
)

(defn opsaet-braet [
                     braetbredde
                     braethoejde
                     braetfigurer
                   ]
  (let [
         tomt-braet (vec (repeat braetbredde (vec (repeat braethoejde nil))))
         braet-med-fire-paa-stribe (opdater-celler tomt-braet (braetfigurer :fire-paa-stribe))
         braet-med-infektion (opdater-celler braet-med-fire-paa-stribe (braetfigurer :infektion))
       ]
       braet-med-infektion
  )
)

(defn slaa-op [braet [j i]]
  (try
    (nth (nth braet j) i)
    (catch Exception E nil)
  )
)

(defn udfoer-loekke-via-atom [
                               loekkefunktion                   ; Den funktion der kaldes inde i loekken
                               tidsenhed                        ; Tidsenhed angivet i millisekunder
                               tidsgraense                      ; Tidsgraensen angivet i millisekunder
                               afbrydelse-til-kalderen          ; Atom med afbrydelsessignal til kalderen
                             ]
  (let [
         fortsaet-loekken (atom true)  ; Saa det kan styres udefra om loekken skal udfoeres mere
         samlet-tid (atom 0)
       ]
       (go
         (loop [
                 akkumuleret-tid 0
               ]
               (try
                 (do
                   (loekkefunktion akkumuleret-tid)
                   (Thread/sleep tidsenhed)
                 )
                 (catch Exception e
                   (reset! fortsaet-loekken false)
                 )
               )
               (if (and (<= akkumuleret-tid tidsgraense) @fortsaet-loekken)
                 (recur
                        (+ akkumuleret-tid tidsenhed)
                 )
                 (do
                   (reset! samlet-tid akkumuleret-tid)
                   (reset! afbrydelse-til-kalderen (> @samlet-tid tidsgraense))
                 )
               )
         )
       )
       {:fortsaet-loekken fortsaet-loekken :samlet-tid samlet-tid}
  )
)

