(ns aoc2017.day2
  (:require [clojure.string :as string]
            [clojure.edn :as edn]
            [clojure.math.combinatorics :as combo]))

;;;; Day 2 ;;;;

;; The spreadsheet consists of rows of apparently-random numbers. To
;; make sure the recovery process is on the right track, they need you
;; to calculate the spreadsheet's checksum. For each row, determine
;; the difference between the largest value and the smallest value;
;; the checksum is the sum of all of these differences.


(defn read-matrix [m]
  (->> m
       (#(string/split % #"\n"))
       (map #(string/split % #"\s+"))
       (map #(map edn/read-string %))
       ))

(defn checksum-matrix [m]
  (->> m
       read-matrix
       (map #(- (apply max %) (apply min %)))
       (reduce +)))


  (def test-spreadsheet
    "5 1 9 5\n7 5 3\n2 4 6 8")

(comment
  ;; For example, given the following spreadsheet:

  ;; 5 1 9 5
  ;; 7 5 3
  ;; 2 4 6 8

  ;; The first row's largest and smallest values are 9 and 1, and
  ;; their difference is 8.

  ;; The second row's largest and smallest values are 7 and 3, and
  ;; their difference is 4.

  ;; The third row's difference is 6.

  ;; In this example, the spreadsheet's checksum would be 8 + 4 + 6 = 18.

  (checksum-matrix test-spreadsheet)
  )

(def day2 (-> "resources/day2.txt" slurp))

(comment
  ;; What is the checksum for the spreadsheet in your puzzle input?
  (checksum-matrix day2)
  )


;;; Part 2 ;;;

;; It sounds like the goal is to find the only two numbers in each row
;; where one evenly divides the other - that is, where the result of
;; the division operation is a whole number. They would like you to
;; find those numbers on each line, divide them, and add up each
;; line's result.

(defn even-checksum-row [r]
  (->> r
       (#(combo/combinations % 2))
       (some (fn [[a b]]
               (cond (zero? (mod a b)) (/ a b)
                     (zero? (mod b a)) (/ b a)
                     :else             nil)))))

(defn even-checksum-matrix [m]
  (->> m
       read-matrix
       (map even-checksum-row)
       (reduce +)))

(def second-test-spreadsheet
  "5 9 2 8\n9 4 7 3\n3 8 6 5")

(comment
  ;; For example, given the following spreadsheet:

  ;; 5 9 2 8
  ;; 9 4 7 3
  ;; 3 8 6 5

  ;; In the first row, the only two numbers that evenly divide are 8 and
  ;; 2; the result of this division is 4.
  (even-checksum-row [5 9 2 8])

  ;; In the second row, the two numbers are 9 and 3; the result is 3.
  (even-checksum-row [9 4 7 3])

  ;; In the third row, the result is 2.
  (even-checksum-row [3 8 6 5])

  ;; In this example, the sum of the results would be 4 + 3 + 2 = 9.
  (even-checksum-matrix second-test-spreadsheet)

  ;; What is the sum of each row's result in your puzzle input?
  (even-checksum-matrix day2)
  )
