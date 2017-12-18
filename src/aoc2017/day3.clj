(ns aoc2017.day3
  (:require [clojure.string :as string]
            [clojure.math.numeric-tower :as math]))

;;;; Day 3 ;;;;

(defn square [n] (* n n))

(defn offset [n]
  (if (= n 1)
    0
    (let [ring        (-> n
                          math/sqrt
                          math/ceil
                          int
                          (#(if (odd? %) (dec %) %))
                          (/ 2)
                          )
          _ (println "Ring:" ring)
          inner       (square (inc ring))
          _ (println "Inner:" inner)
          outer       (square (+ 3 ring))
          _ (println "Outer:" outer)
          side-length (-> outer (- inner) (/ 4))
          _ (println side-length)
          offset      (-> n
                          (- inner)
                          (mod side-length)
                          (- (/ side-length 2))
                          math/abs)]
      (+ ring offset))))
