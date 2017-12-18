(ns aoc2017.day4
  (:require [clojure.string :as string]))

(def day4-input (slurp "resources/day4.txt"))


;;;; Day 4 ;;;;

;; A new system policy has been put in place that requires all
;; accounts to use a passphrase instead of simply a password. A
;; passphrase consists of a series of words (lowercase letters)
;; separated by spaces.

;; The system's full passphrase list is available as your puzzle
;; input. How many passphrases are valid?

;; To ensure security, a valid passphrase must contain no duplicate words.

(defn unique-words? [pp]
  (let [words   (string/split pp #"\s+")
        uniques (set words)]
    (= (count words) (count uniques))))

(defn check-passphrases [validator pps]
  (->> (string/split pps #"\n")
       (filter validator)
       count))

(comment
  (check-passphrases unique-words? day4-input))

;; Part 2

;; For added security, yet another system policy has been put in
;; place. Now, a valid passphrase must contain no two words that are
;; anagrams of each other - that is, a passphrase is invalid if any
;; word's letters can be rearranged to form any other word in the
;; passphrase.

;; Under this new system policy, how many passphrases are valid?

(defn no-anagrams? [pp]
  (let [words      (string/split pp #"\s+")
        normalized (->> words (map sort) set)]
    (= (count words) (count normalized))))

(comment
  (check-passphrases no-anagrams? day4-input))
