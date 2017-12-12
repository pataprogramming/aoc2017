(ns aoc2017.core
  (:require [clojure.string :as string]
            [clojure.edn :as edn]))

;; Day 1
(defn digitize [d]
  (->> d str (map #(- (int %) (int \0)))))

(defn solve-captcha [d]
  (->> d
       digitize
       (#(concat % [(first %)]))
       (partition 2 1)
       (filter (fn [[a b]] (= a b)))
       (map first)
       (reduce +)))

(defn solve-halfway-captcha [d]
  (->> d
       digitize
       (#(map vector % (->> (concat % %)
                            (drop (/ (count %) 2))
                            (take (count %)))))
       (filter (fn [[a b]] (= a b)))
       (map first)
       (reduce +)))

;; Day 2
(defn dematrix [m]
  (->> m
       (#(string/split % #"\n"))
       (map #(string/split % #"\s+"))
       (map #(map edn/read-string %))
       ))

(defn checksum-matrix [m]
  (->> m
       dematrix
       (map #(- (apply max %) (apply min %)))
       (reduce +)))
