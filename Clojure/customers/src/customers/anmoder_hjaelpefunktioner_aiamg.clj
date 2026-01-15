(ns customers.anmoder-hjaelpefunktioner-aiamg
  (:require (customers [anmoder-hjaelpefunktioner-diverse :as anmoder-hjlp-div]))
  (:require [clojure.core.async :refer [go >!]])
  (:import
	(java.awt Color)
	(java.awt.event MouseEvent)
	(Aiamg Camera Polygon3D Point3D Line3D)
        (Aiamg.Utils ProjectionType DistanceType)
  )
)

(def projektionsplanets-z-vaerdi 100.0)

(defn ny-musehaendelsesbehandler [
                                   grafikmodul
				   kanal			; atom
				 ]
  (fn [musehaendelse]
    (if (not= nil musehaendelse)
      (if (= (.getButton musehaendelse) MouseEvent/BUTTON1)
        (do
	  (dosync
	    ((@(grafikmodul :funktionalitet) :vaelg-fokuseret-celle))
            ((@(grafikmodul :funktionalitet) :rens-laerred-tegn-og-vis-alt))
	  )
	  (let [
                 valgte-celler ((@(grafikmodul :funktionalitet) :hent-valgte-celler))
               ]
            (if (>= (count valgte-celler) 1)
	      (go (>! @kanal [(:kolonneindeks (first valgte-celler)) (:raekkeindeks (first valgte-celler))]))
            )
	  )
	)
      )
    )
  )
)

(defn ny-musebevaegelsehaendelsesbehandler [grafikmodul]
  (fn [musebevaegelseshaendelse]
    (dosync
      ((@(grafikmodul :funktionalitet) :fokuser-paa-celle) musebevaegelseshaendelse)
      ((@(grafikmodul :funktionalitet) :rens-laerred-tegn-og-vis-alt))
    )
  )
)

(defn ny-braetcelle [
                      x0
		      y0
		      x1
		      y1
		      margenpct
		      cellerammefarve
		      brikfarve
		    ]
  (let [
         x0-d (double x0) ;;; x0 kan for eksempel vaere -324N som kan give problemer i "Math/abs".
         x1-d (double x1)
         y0-d (double y0)
         y1-d (double y1)

         margen-x (Math/round (* (/ margenpct 100) (Math/abs (- x1-d x0-d))))
         margen-y (Math/round (* (/ margenpct 100) (Math/abs (- y0-d y1-d))))

         oevre-cellekant (doto (new Polygon3D)
                           (.addPoint (new Point3D (+ x0-d 0) (+ y0-d 0) projektionsplanets-z-vaerdi (:top cellerammefarve)))
			   (.addPoint (new Point3D (- x1-d 0) (+ y0-d 0) projektionsplanets-z-vaerdi (:top cellerammefarve)))
			   (.addPoint (new Point3D (- x1-d 0) (- y0-d margen-y) projektionsplanets-z-vaerdi (:top cellerammefarve)))
			   (.addPoint (new Point3D (+ x0-d 0) (- y0-d margen-y) projektionsplanets-z-vaerdi (:top cellerammefarve)))
                   )
         nedre-cellekant (doto (new Polygon3D)
                           (.addPoint (new Point3D (+ x0-d 0) (+ y1-d 0) projektionsplanets-z-vaerdi (:bund cellerammefarve)))
			   (.addPoint (new Point3D (- x1-d 0) (+ y1-d 0) projektionsplanets-z-vaerdi (:bund cellerammefarve)))
			   (.addPoint (new Point3D (- x1-d 0) (+ y1-d margen-y) projektionsplanets-z-vaerdi (:bund cellerammefarve)))
			   (.addPoint (new Point3D (+ x0-d 0) (+ y1-d margen-y) projektionsplanets-z-vaerdi (:bund cellerammefarve)))
                     )
         venstre-cellekant (doto (new Polygon3D)
                           (.addPoint (new Point3D (+ x0-d 0) (- y0-d margen-y 0) projektionsplanets-z-vaerdi (:venstre cellerammefarve)))
			   (.addPoint (new Point3D (+ x0-d margen-x) (- y0-d margen-y 0) projektionsplanets-z-vaerdi (:venstre cellerammefarve)))
			   (.addPoint (new Point3D (+ x0-d margen-x) (+ y1-d margen-y 0) projektionsplanets-z-vaerdi (:venstre cellerammefarve)))
			   (.addPoint (new Point3D (+ x0-d 0) (+ y1-d margen-y 0) projektionsplanets-z-vaerdi (:venstre cellerammefarve)))
                     )
         hoejre-cellekant (doto (new Polygon3D)
                           (.addPoint (new Point3D (- x1-d margen-x) (- y0-d margen-y 0) projektionsplanets-z-vaerdi (:hoejre cellerammefarve)))
			   (.addPoint (new Point3D (- x1-d 0) (- y0-d margen-y 0) projektionsplanets-z-vaerdi (:hoejre cellerammefarve)))
			   (.addPoint (new Point3D (- x1-d 0) (+ y1-d margen-y 0) projektionsplanets-z-vaerdi (:hoejre cellerammefarve)))
			   (.addPoint (new Point3D (- x1-d margen-x) (+ y1-d margen-y 0) projektionsplanets-z-vaerdi (:hoejre cellerammefarve)))
                     )
	 cellebrik (doto (new Polygon3D)
                           (.addPoint (new Point3D (+ x0-d margen-x 1) (- y0-d margen-y 0) projektionsplanets-z-vaerdi brikfarve))
			   (.addPoint (new Point3D (- x1-d margen-x 1) (- y0-d margen-y 0) projektionsplanets-z-vaerdi brikfarve))
			   (.addPoint (new Point3D (- x1-d margen-x 1) (+ y1-d margen-y 0) projektionsplanets-z-vaerdi brikfarve))
			   (.addPoint (new Point3D (+ x0-d margen-x 1) (+ y1-d margen-y 0) projektionsplanets-z-vaerdi brikfarve))
                   )
       ]
       {:oevre-cellekant oevre-cellekant :nedre-cellekant nedre-cellekant :venstre-cellekant venstre-cellekant :hoejre-cellekant hoejre-cellekant :cellebrik cellebrik}
  )
)

(defn beregn-basisramme [
                          vinduesbredde
			  vindueshoejde
			]
  (let [
         vinduets-venstre-kant (* -1 (/ vinduesbredde 2))
         vinduets-hoejre-kant (/ vinduesbredde 2)
         vinduets-nedre-kant (* -1 (/ vindueshoejde 2))
         vinduets-oevre-kant (/ vindueshoejde 2)

         vinduets-oevre-margenpct 10
         vinduets-nedre-margenpct 10
         vinduets-venstre-margenpct 10
         vinduets-hoejre-margenpct 10
       ]
       {
         :venstre-basisrammekant (* vinduets-venstre-kant (/ (- 100 vinduets-venstre-margenpct) 100))
         :oevre-basisrammekant (* vinduets-oevre-kant (/ (- 100 vinduets-oevre-margenpct) 100))
         :hoejre-basisrammekant (* vinduets-hoejre-kant (/ (- 100 vinduets-hoejre-margenpct) 100))
         :nedre-basisrammekant (* vinduets-nedre-kant (/ (- 100 vinduets-nedre-margenpct) 100))
       }
  )
)

(defn beregn-ekstraramme [
                            venstre-rammekant		; I skaermpunkter (pixels)
			    hoejre-rammekant
			    oevre-rammekant
			    nedre-rammekant
                            nedtaellingsramme-venstre-margen-pctr ; [50 10 85 0]
			  ]
  (let [
         venstre-rammemargenpct (nth nedtaellingsramme-venstre-margen-pctr 0)	; I procent, mellem 0 og 100
	 hoejre-rammemargenpct (nth nedtaellingsramme-venstre-margen-pctr 1)
	 oevre-rammemargenpct (nth nedtaellingsramme-venstre-margen-pctr 2)
	 nedre-rammemargenpct (nth nedtaellingsramme-venstre-margen-pctr 3)
       ]
       {
         :ramme-x0 (+ venstre-rammekant (* (- hoejre-rammekant venstre-rammekant) (/ venstre-rammemargenpct 100)))
         :ramme-y0 (+ nedre-rammekant (* (- oevre-rammekant nedre-rammekant) (/ nedre-rammemargenpct 100)))
         :ramme-x1 (- hoejre-rammekant (* (- hoejre-rammekant venstre-rammekant) (/ hoejre-rammemargenpct 100)))
         :ramme-y1 (- oevre-rammekant (* (- oevre-rammekant nedre-rammekant) (/ oevre-rammemargenpct 100)))
       }
  )
)

(defn vis-ekstraramme [
                       kamera		; Aiamg.Camera
                       ekstraramme	; {:ramme-x0 :ramme-y0 :ramme-x1 :ramme-y1}
		     ]
  (let [
         rammefarve Color/gray
         venstre-ekstrarammelinie (new Line3D (new Point3D (:ramme-x0 ekstraramme) (:ramme-y0 ekstraramme) projektionsplanets-z-vaerdi rammefarve)
	                                 (new Point3D (:ramme-x0 ekstraramme) (:ramme-y1 ekstraramme) projektionsplanets-z-vaerdi rammefarve))
	 oevre-ekstrarammelinie (new Line3D (new Point3D (:ramme-x0 ekstraramme) (:ramme-y1 ekstraramme) projektionsplanets-z-vaerdi rammefarve)
	                                (new Point3D (:ramme-x1 ekstraramme) (:ramme-y1 ekstraramme) projektionsplanets-z-vaerdi rammefarve))
	 hoejre-ekstrarammelinie (new Line3D (new Point3D (:ramme-x1 ekstraramme) (:ramme-y0 ekstraramme) projektionsplanets-z-vaerdi rammefarve)
	                                  (new Point3D (:ramme-x1 ekstraramme) (:ramme-y1 ekstraramme) projektionsplanets-z-vaerdi rammefarve))
	 nedre-ekstrarammelinie (new Line3D (new Point3D (:ramme-x0 ekstraramme) (:ramme-y0 ekstraramme) projektionsplanets-z-vaerdi rammefarve)
	                                   (new Point3D (:ramme-x1 ekstraramme) (:ramme-y0 ekstraramme) projektionsplanets-z-vaerdi rammefarve))
       ]
       (doto kamera
	 (.updateScene venstre-ekstrarammelinie ProjectionType/ORTHOGRAPHIC)
         (.updateScene oevre-ekstrarammelinie ProjectionType/ORTHOGRAPHIC)
	 (.updateScene hoejre-ekstrarammelinie ProjectionType/ORTHOGRAPHIC)
	 (.updateScene nedre-ekstrarammelinie ProjectionType/ORTHOGRAPHIC)
       )
  )
)

(defn vis-nedtaelling [
                        kamera			; Aiamg.Camera
		        spillerbrik		; Spiller1: "*", Spiller2: "¤"
		        samlet-tidsforbrug	; Det samlede tidsforbrug
		        tidsgraense		; Tidsgraensen
		        nedtaellingsramme-x0	; Nedtaellingsrammens origo-x
		        nedtaellingsramme-y0	; Nedtaellingsrammens origo-y
    		        nedtaellingsramme-x1	; Nedtaellingsrammens hoejre graense
		        nedtaellingsramme-y1	; Nedtaellingsrammens oevre graense
                      ]
  (if (< samlet-tidsforbrug tidsgraense)
    (let [
 	   brikfarve (if (= spillerbrik "*") Color/white Color/red)
           broekdel (/ samlet-tidsforbrug tidsgraense)
	   yr (+ (* nedtaellingsramme-y1 (- 1 broekdel)) (* nedtaellingsramme-y0 broekdel))
	   yr-oevre-graense (if (> yr nedtaellingsramme-y1) (- yr 1) yr)
	   nedtaellingsfyld (doto (new Polygon3D)
                                  (.addPoint (new Point3D nedtaellingsramme-x0 nedtaellingsramme-y0 projektionsplanets-z-vaerdi brikfarve))
                                  (.addPoint (new Point3D nedtaellingsramme-x1 nedtaellingsramme-y0 projektionsplanets-z-vaerdi brikfarve))
                                  (.addPoint (new Point3D nedtaellingsramme-x1 yr-oevre-graense projektionsplanets-z-vaerdi brikfarve))
                                  (.addPoint (new Point3D nedtaellingsramme-x0 yr-oevre-graense projektionsplanets-z-vaerdi brikfarve))
                            )
         ]
         (doto kamera
	   (.updateScene nedtaellingsfyld ProjectionType/ORTHOGRAPHIC)
         )
    )
  )
)

(defn beregn-cellegitterkoordinater [
                                      braetbredde
				      braethoejde
				      basisramme
				    ]
  (let [
         venstre-basisramme-margenpct 10
         oevre-basisramme-margenpct 30
         hoejre-basisramme-margenpct 10
         nedre-basisramme-margenpct 10
	 
         venstre-cellegitterkant (* (:venstre-basisrammekant basisramme) (/ (- 100 venstre-basisramme-margenpct) 100))
         hoejre-cellegitterkant (* (:hoejre-basisrammekant basisramme) (/ (- 100 hoejre-basisramme-margenpct) 100))
         nedre-cellegitterkant (* (:nedre-basisrammekant basisramme) (/ (- 100 nedre-basisramme-margenpct) 100))
         oevre-cellegitterkant (* (:oevre-basisrammekant basisramme) (/ (- 100 oevre-basisramme-margenpct) 100))

	 raekkeafstand-i-cellegitter 0
	 kolonneafstand-i-cellegitter 0
	 
	 delta-x-i-cellegitter (/ (- (- hoejre-cellegitterkant venstre-cellegitterkant) (* (- braetbredde 1) kolonneafstand-i-cellegitter)) braetbredde)
	 delta-y-i-cellegitter (/ (- (- oevre-cellegitterkant nedre-cellegitterkant) (* (- braethoejde 1) raekkeafstand-i-cellegitter)) braethoejde)

         beregn-cellegitterkoordinater-raekke-i (fn [i y0]
	                              (loop [
	                                      j 0
	                                      x0 venstre-cellegitterkant
		                              cellekoordinatsraekke [{:raekkeindeks i :oevre-cellekant y0 :nedre-cellekant (- y0 delta-y-i-cellegitter)
				                                      :kolonneindeks j :venstre-cellekant x0 :hoejre-cellekant (+ x0 delta-x-i-cellegitter)}]
	                                    ]
	                                    (if (< j (- braetbredde 1))
			                        (recur (+ j 1)
			                               (+ x0 delta-x-i-cellegitter kolonneafstand-i-cellegitter)
					               (conj cellekoordinatsraekke {:raekkeindeks i
						                                    :oevre-cellekant y0
					                                            :nedre-cellekant (- y0 delta-y-i-cellegitter)
				                                                    :kolonneindeks (+ j 1)
						  		                    :venstre-cellekant (+ x0 delta-x-i-cellegitter kolonneafstand-i-cellegitter)
								                    :hoejre-cellekant (+ x0 delta-x-i-cellegitter kolonneafstand-i-cellegitter delta-x-i-cellegitter)
	                                                                           }
					               )
		                                )
			                        cellekoordinatsraekke
			                    )
	                              )
	                            )
       ]
       (let [cellekoordinater (loop [
                                      i (- braethoejde 1)
                                      y0 oevre-cellegitterkant
	                              cellekoordinater-raekkeopdelt []
	                            ]
	                       (let [
			              cellekoordinater-raekke-i (beregn-cellegitterkoordinater-raekke-i i y0)
				    ]
	                            (if (> i 0)
	                                (recur
					  (- i 1)
					  (- y0 delta-y-i-cellegitter raekkeafstand-i-cellegitter)
					  (concat cellekoordinater-raekkeopdelt cellekoordinater-raekke-i)
					)
		                        (concat cellekoordinater-raekkeopdelt cellekoordinater-raekke-i)
	                            )
	                      )
                         )
            ]
	    {
	      :kantkoordinater {
	                       :venstre venstre-cellegitterkant
                               :hoejre hoejre-cellegitterkant
                               :bund nedre-cellegitterkant
                               :top oevre-cellegitterkant
			     }
	      :cellekoordinater cellekoordinater
	    }
     )
  )
)

(defn find-cellekoordinat [x y cellekoordinater]
  (let [
         pred #(and (>= x (:venstre-cellekant %)) (< x (:hoejre-cellekant %))
                    (>= y (:nedre-cellekant %)) (< y (:oevre-cellekant %)))
	 cellekoordinat (first (filter pred cellekoordinater))
       ]
       cellekoordinat
  )
)

(defn opdater-scene-fra-cellekoordinater [
                                           braet
                                           kamera
				           cellekoordinater
				           valgte-celleindekser
				           fokuseret-celleindeks
				           fokuseret-cellerammefarve
				         ]
  (doseq [cellekoordinat cellekoordinater]
         (let [
	        celleindeks {:raekkeindeks (:raekkeindeks cellekoordinat) :kolonneindeks (:kolonneindeks cellekoordinat)}
	        j (:kolonneindeks cellekoordinat)
		i (:raekkeindeks cellekoordinat)
		braetsymbol (anmoder-hjlp-div/slaa-op braet [j i])
		brikfarve (if (= braetsymbol "*")
			       Color/white
			       (if (= braetsymbol "¤")
				   Color/red
				   Color/darkGray
			       )
			   )
		filtrering-ud-fra-valg (filter #(and (= (:raekkeindeks %) i) (= (:kolonneindeks %) j)) valgte-celleindekser)
		valgt? (> (count filtrering-ud-fra-valg) 0)
		musefokus? (and (= (:raekkeindeks fokuseret-celleindeks) i) (= (:kolonneindeks fokuseret-celleindeks) j))
		cellerammefarve (if musefokus?
		                   fokuseret-cellerammefarve
		                   (if valgt?
				     {:top brikfarve :bund brikfarve :venstre brikfarve :hoejre brikfarve}
				     {:top Color/blue :bund Color/blue :venstre Color/blue :hoejre Color/blue}
				   )
				 )
	        venstre-cellekant (:venstre-cellekant cellekoordinat)
		hoejre-cellekant (:hoejre-cellekant cellekoordinat)
		oevre-cellekant (:oevre-cellekant cellekoordinat)
		nedre-cellekant (:nedre-cellekant cellekoordinat)
		braetcelle-margenpct 12
                braetcelle (ny-braetcelle venstre-cellekant oevre-cellekant hoejre-cellekant nedre-cellekant braetcelle-margenpct cellerammefarve brikfarve)
	      ]
              (doto kamera
	        (.updateScene (:oevre-cellekant braetcelle ProjectionType/ORTHOGRAPHIC))
		(.updateScene (:nedre-cellekant braetcelle ProjectionType/ORTHOGRAPHIC))
		(.updateScene (:venstre-cellekant braetcelle ProjectionType/ORTHOGRAPHIC))
		(.updateScene (:hoejre-cellekant braetcelle ProjectionType/ORTHOGRAPHIC))
		(.updateScene (:cellebrik braetcelle ProjectionType/ORTHOGRAPHIC))
	      )
	 )
  )
)

(defn vis-braet [
                  braet
		  kamera
		  basisramme
		  kantkoordinater
		  cellekoordinater
		  valgte-celler-indekseret
		  fokuseret-celle-indekseret
		  fokuseret-celle-rammefarve
		]
  (let [
         venstre-basisrammelinie (new Line3D (new Point3D (:venstre-basisrammekant basisramme) (:oevre-basisrammekant basisramme) projektionsplanets-z-vaerdi Color/red)
	                                     (new Point3D (:venstre-basisrammekant basisramme) (:nedre-basisrammekant basisramme) projektionsplanets-z-vaerdi Color/red))
	 oevre-basisrammelinie (new Line3D (new Point3D (:venstre-basisrammekant basisramme) (:oevre-basisrammekant basisramme) projektionsplanets-z-vaerdi Color/red)
	                                   (new Point3D (:hoejre-basisrammekant basisramme) (:oevre-basisrammekant basisramme) projektionsplanets-z-vaerdi Color/red))
	 hoejre-basisrammelinie (new Line3D (new Point3D (:hoejre-basisrammekant basisramme) (:oevre-basisrammekant basisramme) projektionsplanets-z-vaerdi Color/red)
	                                    (new Point3D (:hoejre-basisrammekant basisramme) (:nedre-basisrammekant basisramme) projektionsplanets-z-vaerdi Color/red))
	 nedre-basisrammelinie (new Line3D (new Point3D (:hoejre-basisrammekant basisramme) (:nedre-basisrammekant basisramme) projektionsplanets-z-vaerdi Color/red)
	                                   (new Point3D (:venstre-basisrammekant basisramme) (:nedre-basisrammekant basisramme) projektionsplanets-z-vaerdi Color/red))

         venstre-cellerammelinie (new Line3D (new Point3D (- (:venstre kantkoordinater) 1) (+ (:top kantkoordinater) 1) projektionsplanets-z-vaerdi Color/gray)
	                                     (new Point3D (- (:venstre kantkoordinater) 1) (- (:bund kantkoordinater) 1) projektionsplanets-z-vaerdi Color/gray))
	 oevre-cellerammelinie (new Line3D (new Point3D (- (:venstre kantkoordinater) 1) (+ (:top kantkoordinater) 1) projektionsplanets-z-vaerdi Color/gray)
	                                   (new Point3D (+ (:hoejre kantkoordinater) 1) (+ (:top kantkoordinater) 1) projektionsplanets-z-vaerdi Color/gray))
	 hoejre-cellerammelinie (new Line3D (new Point3D (+ (:hoejre kantkoordinater) 1) (+ (:top kantkoordinater) 1) projektionsplanets-z-vaerdi Color/gray)
	                                    (new Point3D (+ (:hoejre kantkoordinater) 1) (- (:bund kantkoordinater) 1) projektionsplanets-z-vaerdi Color/gray))
	 nedre-cellerammelinie (new Line3D (new Point3D (+ (:hoejre kantkoordinater) 1) (- (:bund kantkoordinater) 1) projektionsplanets-z-vaerdi Color/gray)
	                                   (new Point3D (- (:venstre kantkoordinater) 1) (- (:bund kantkoordinater) 1) projektionsplanets-z-vaerdi Color/gray))
       ]
       (doto kamera
	 (.updateScene venstre-basisrammelinie ProjectionType/ORTHOGRAPHIC)
         (.updateScene oevre-basisrammelinie ProjectionType/ORTHOGRAPHIC)
	 (.updateScene hoejre-basisrammelinie ProjectionType/ORTHOGRAPHIC)
	 (.updateScene nedre-basisrammelinie ProjectionType/ORTHOGRAPHIC)
	 (.updateScene venstre-cellerammelinie ProjectionType/ORTHOGRAPHIC)
	 (.updateScene oevre-cellerammelinie ProjectionType/ORTHOGRAPHIC)
	 (.updateScene hoejre-cellerammelinie ProjectionType/ORTHOGRAPHIC)
	 (.updateScene nedre-cellerammelinie ProjectionType/ORTHOGRAPHIC)
       )
       (opdater-scene-fra-cellekoordinater braet kamera cellekoordinater valgte-celler-indekseret fokuseret-celle-indekseret fokuseret-celle-rammefarve)
  )
)

(defn omform-koordinater-i-musehaendelse [
                                           kamera
					   indsatser
					   musehaendelse
					 ]
  (if (= nil musehaendelse)
    {
      :omformet-x nil
      :omformet-y nil
    }
    (let [
           mh-x (.getX musehaendelse)
           mh-y (.getY musehaendelse)
	   indsatser-top (.top indsatser)
	   indsatser-venstre (.left indsatser)
	   mh-w (+ mh-x indsatser-venstre)
	   mh-h (- mh-y indsatser-top)
	   punkt-i-projektionsplanet (.getPointInProjectionPlaneFromPointOnScreen kamera mh-w mh-h)
         ]
         {
           :omformet-x (.x punkt-i-projektionsplanet)
           :omformet-y (.y punkt-i-projektionsplanet)
         }
    )
  )
)

(defn nyt-kamera [vinduesbredde vindueshoejde]
  (new Camera 10.0 projektionsplanets-z-vaerdi vinduesbredde vindueshoejde vinduesbredde vindueshoejde (/ vinduesbredde 2) (/ vindueshoejde 2) DistanceType/Z_COORD)
)

;;; Grafikmodul ;;;

(def grafikmodul
  (let [
         synkroniseringsref (ref {
	                           :punktgitter nil
				   :vis-scene nil
				 }
		            )
         tilstand (ref {
	                 :braet nil
                         :braetfigurer nil
                         :kamera nil
		         :basisramme nil
		         :cellegitterkoordinater nil
                         :valgte-celler-indekseret nil
                         :fokuseret-celle-indekseret nil
                         :fokuseret-celle-rammefarve nil
                         :nedtaellingsramme-spiller nil
			 :samlet-tid-for-anmodning nil
			 :tidsgraense nil
                       }
		  )
         funktionalitet (atom {
	                        :fastsaet-tilstand (fn [
				                         braet
                                                         braetfigurer
						         vinduesbredde
				                         vindueshoejde
						         braetbredde
						         braethoejde
						         nedtaellingsramme-venstre-margen-pctr	; [50 10 85 0]
							 tidsgraense
						       ]
						     (let [
						            kamera (nyt-kamera vinduesbredde vindueshoejde)
							    basisramme (beregn-basisramme vinduesbredde vindueshoejde)
							    cellegitterkoordinater (beregn-cellegitterkoordinater braetbredde braethoejde basisramme)
							    kantkoordinater (:kantkoordinater cellegitterkoordinater)
							    cellekoordinater (:cellekoordinater cellegitterkoordinater)
							    nedtaellingsramme-venstre (beregn-ekstraramme (:venstre-basisrammekant basisramme) (:venstre kantkoordinater) (:top kantkoordinater) (- (:bund kantkoordinater) 1) nedtaellingsramme-venstre-margen-pctr)
							    nedtaellingsramme-spiller nedtaellingsramme-venstre
							    samlet-tid-for-anmodning 0
						          ]
							  (dosync
						            (alter tilstand assoc :braet braet
                                                                                  :braetfigurer braetfigurer
							                          :kamera kamera
								                  :basisramme basisramme
								                  :cellegitterkoordinater cellegitterkoordinater
								  	          :valgte-celler-indekseret []
									          :fokuseret-celle-indekseret nil
									          :fokuseret-celle-rammefarve nil
									          :nedtaellingsramme-spiller nedtaellingsramme-spiller
									          :samlet-tid-for-anmodning samlet-tid-for-anmodning
									          :tidsgraense tidsgraense
						            )
							  )
						     )
					           )
				:opdater-tilstand (fn [
				                        noegle
							vaerdi
						      ]
						    (dosync (alter tilstand assoc noegle vaerdi))
				                  )
				:vaelg-fokuseret-celle (fn []
				                         (let [
					                        valgte-celler-indekseret (@tilstand :valgte-celler-indekseret)
						                opdaterede-valgte-celler-indekseret (if (= (@tilstand :fokuseret-celle-indekseret) nil)
						                                                      valgte-celler-indekseret
						                                                      (conj valgte-celler-indekseret (@tilstand :fokuseret-celle-indekseret))
											            )
					                      ]
							      (dosync (alter tilstand assoc :valgte-celler-indekseret opdaterede-valgte-celler-indekseret))
					                 )
				                       )
				:fokuser-paa-celle (fn [
				                         musebevaegelseshaendelse
						       ]
						     (if (not= nil musebevaegelseshaendelse)
				                       (let [
						              kamera (@(grafikmodul :tilstand) :kamera)
							      indsats (.getInsetsOnScreen kamera)
		                                              projektionsplanskoordinater (omform-koordinater-i-musehaendelse kamera indsats musebevaegelseshaendelse)
			                                      projektionsplan-x (:omformet-x projektionsplanskoordinater)
			                                      projektionsplan-y (:omformet-y projektionsplanskoordinater)
							      kantkoordinater ((@(grafikmodul :tilstand) :cellegitterkoordinater) :kantkoordinater)
		                                            ]
		                                            (if (and (not= nil projektionsplan-x) (>= projektionsplan-x (double (:venstre kantkoordinater))) (<= projektionsplan-x (double (:hoejre kantkoordinater)))
		                                                     (not= nil projektionsplan-y) (>= projektionsplan-y (double (:bund kantkoordinater))) (<= projektionsplan-y (double (:top kantkoordinater)))
		                                                )
							      (let [
							             cellekoordinater ((@(grafikmodul :tilstand) :cellegitterkoordinater) :cellekoordinater)
					                             cellekoordinat-i-fokus (find-cellekoordinat projektionsplan-x projektionsplan-y cellekoordinater)
					                             celleindeks-i-fokus {:raekkeindeks (:raekkeindeks cellekoordinat-i-fokus) :kolonneindeks (:kolonneindeks cellekoordinat-i-fokus)}
                                                                     predikat #(and (= (:kolonneindeks celleindeks-i-fokus) ((% :koordinat) 0))
                                                                                    (= (:raekkeindeks celleindeks-i-fokus) ((% :koordinat) 1)))
						                   ]
                                                                   (if (or (some predikat ((tilstand :braetfigurer) :fire-paa-stribe))
                                                                           (some predikat ((tilstand :braetfigurer) :infektion)))
						                     (do
								       ((@(grafikmodul :funktionalitet) :opdater-tilstand) :fokuseret-celle-indekseret celleindeks-i-fokus)
							               ((@(grafikmodul :funktionalitet) :opdater-tilstand) :fokuseret-celle-rammefarve {:top Color/gray :bund Color/gray :venstre Color/gray :hoejre Color/gray})
							             )
                                                                     (do
								       ((@(grafikmodul :funktionalitet) :opdater-tilstand) :fokuseret-celle-indekseret nil)
							               ((@(grafikmodul :funktionalitet) :opdater-tilstand) :fokuseret-celle-rammefarve nil)
							             )
                                                                   )
							      )
							      (do
								((@(grafikmodul :funktionalitet) :opdater-tilstand) :fokuseret-celle-indekseret nil)
							        ((@(grafikmodul :funktionalitet) :opdater-tilstand) :fokuseret-celle-rammefarve nil)
							      )
				                            )
						       )
						     )
						   )
				:hent-valgte-celler (fn [] (@tilstand :valgte-celler-indekseret))
				:fravaelg-alle-valgte-celler (fn [] (dosync (alter tilstand assoc :valgte-celler-indekseret [])))
				:rens-laerred-tegn-og-vis-alt (fn []
                                                                (dosync
                                                                  (alter synkroniseringsref assoc :punktgitter (.clearRaster (@tilstand :kamera)))
				                                  (let [
								         samlet-tid-for-anmodning (@tilstand :samlet-tid-for-anmodning)
								         tidsgraense (@tilstand :tidsgraense)
							                 braet (@tilstand :braet)
				                                         kantkoordinater ((@tilstand :cellegitterkoordinater) :kantkoordinater)
					                                 cellekoordinater ((@tilstand :cellegitterkoordinater) :cellekoordinater)
						                         kamera (@tilstand :kamera)
						                         nedtaellingsramme-spiller (@tilstand :nedtaellingsramme-spiller)
				                                       ]
			                                               (vis-braet braet kamera (@tilstand :basisramme) kantkoordinater cellekoordinater (@tilstand :valgte-celler-indekseret) (@tilstand :fokuseret-celle-indekseret) (@tilstand :fokuseret-celle-rammefarve))
		         			                       (vis-ekstraramme kamera nedtaellingsramme-spiller)
						                       (vis-nedtaelling kamera "*" samlet-tid-for-anmodning tidsgraense (+ (:ramme-x0 nedtaellingsramme-spiller) 1) (+ (:ramme-y0 nedtaellingsramme-spiller) 1) (- (:ramme-x1 nedtaellingsramme-spiller) 1) (- (:ramme-y1 nedtaellingsramme-spiller) 1))
				                                  )
			                                        )
                                                                (alter synkroniseringsref assoc :vis-scene (.showScene (@tilstand :kamera)))
                                                              )
                              }
			)
       ]
       {:tilstand tilstand :funktionalitet funktionalitet}
  )
)
