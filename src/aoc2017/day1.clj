(ns aoc2017.day1
  (:require [clojure.string :as string]
            [clojure.edn :as edn]
            [clojure.math.combinatorics :as combo]))

;;;; Day 1 ;;;;

;; The captcha requires you to review a sequence of digits (your
;; puzzle input) and find the sum of all digits that match the next
;; digit in the list. The list is circular, so the digit after the
;; last digit is the first digit in the list.

(defn rotate [n coll]
  "Given a collection coll, rotate it by n positions."
  (->> coll cycle (drop n) (take (count coll))))

(defn pair-with [transform s]
  "Given a sequence s and and transform that, when applied to s,
   produces a sequence of the same length, return a sequence of pairs.
   Each pair will be a vector an element of s and its corresponding
   element in the transformed sequence."
  (map vector s (transform s)))

(defn digitize [number]
  "Given an integer number, return a sequenceb of its digits."
  (->> number str (map #(- (int %) (int \0)))))

(defn solve-captcha [transform number]
  (->> number
       digitize
       (pair-with transform)
       (filter (fn [[a b]] (= a b)))
       (map first)
       (reduce +)
       ))

(defn solve-captcha-next [number]
  (solve-captcha (partial rotate 1) number))

(comment
  ;; 1122 produces a sum of 3 (1 + 2) because the first digit (1)
  ;; matches the second digit and the third digit (2) matches the
  ;; fourth digit.
  (solve-captcha-next "1122")

  ;; 1111 produces 4 because each digit (all 1) matches the next.
  (solve-captcha-next "1111")

  ;; 1234 produces 0 because no digit matches the next.
  (solve-captcha-next "1234")

  ;; 91212129 produces 9 because the only digit that matches the next
  ;; one is the last digit, 9.
  (solve-captcha-next "91212129")
  )

(def day1 (-> "resources/day1.txt" slurp edn/read-string))

(comment
  ;; What is the solution to your captcha?

  (solve-captcha-next day1)
  )

;; Part 2

;; Now, instead of considering the next digit, it wants you to
;; consider the digit halfway around the circular list. That is, if
;; your list contains 10 items, only include a digit in your sum if
;; the digit 10/2 = 5 steps forward matches it. Fortunately, your list
;; has an even number of elements

(defn rotate-halfway [coll]
  (rotate (/ (count coll) 2) coll))

(defn solve-captcha-halfway [number]
  (solve-captcha rotate-halfway number))

(comment

  ;; 1212 produces 6: the list contains 4 items, and all four digits
  ;; match the digit 2 items ahead.
  (solve-captcha-halfway "1212")

  ;; 1221 produces 0, because every comparison is between a 1 and a 2.
  (solve-captcha-halfway "1221")

  ;; 123425 produces 4, because both 2s match each other, but no other
  ;; digit has a match.
  (solve-captcha-halfway "123425")

  ;; 123123 produces 12.
  (solve-captcha-halfway "123123")

  ;; 12131415 produces 4.
  (solve-captcha-halfway "12131415")

  ;; What is the solution to your new captcha?
  (solve-captcha-halfway day1)
)
