(ns aoc2017.day5
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))

;;;; Day 5 ;;;;

;; The message includes a list of the offsets for each jump. Jumps are
;; relative: -1 moves to the previous instruction, and 2 skips the
;; next one. Start at the first instruction in the list. The goal is
;; to follow the jumps until one leads outside the list.

;; In addition, these instructions are a little strange; after each
;; jump, the offset of that instruction increases by 1. So, if you
;; come across an offset of 3, you would move three instructions
;; forward, but change it to a 4 for the next time it is encountered.

;; Positive jumps ("forward") move downward; negative jumps move
;; upward. For legibility in this example, these offset values will be
;; written all on one line, with the current instruction marked in
;; parentheses.

;; How many steps does it take to reach the exit?

(defn run-program
  ([program]
   (run-program program inc))
  ([program adjust-fn]
   (letfn [(out-of-bounds? [i] (or (< i 0) (>= i (count program))))]
     (loop [memory         (transient program)
            ip             0
            steps          0]
       (if (out-of-bounds? ip)
         steps
         (let [offset  (get memory ip)
               next-ip (+ ip offset)]
           (recur (assoc! memory ip (adjust-fn offset)) next-ip (inc steps))))))))

(defn read-program [filename]
  (with-open [r (io/reader filename)]
    (->> r line-seq (map edn/read-string) vec)))

(comment
  (run-program (read-program "resources/day5.txt"))
  )

;;; Part 2 ;;;

;; Now, the jumps are even stranger: after each jump, if the offset
;; was three or more, instead decrease it by 1. Otherwise, increase it
;; by 1 as before.

;; How many steps does it now take to reach the exit?

(defn stranger-adjustment [o]
  (if (>= o 3) (dec o) (inc o)))

(comment
  ;; Slow! Switching to a transient drops runtime from 8.9s to 4.3s
  (run-program [0 3 0 1 -3] stranger-adjustment)
  (run-program [0 3 0 1 -3] inc)
  (run-program (read-program "resources/day5.txt") stranger-adjustment)
  )

(defn run-program-fast
  ([program]
   (run-program-fast program inc))
  ([program adjust-fn]
   (letfn [(out-of-bounds? [i] (or (< i 0) (>= i (count program))))]
     (loop [memory         (int-array program)
            ip             0
            steps          0]
       (if (out-of-bounds? ip)
         steps
         (let [offset  (get memory ip)
               next-ip (+ ip offset)]
           (recur (aset-int memory ip (int (adjust-fn offset))) next-ip (inc steps))))))))

(comment
  (run-program (read-program "resources/day5.txt") stranger-adjustment)
  )
