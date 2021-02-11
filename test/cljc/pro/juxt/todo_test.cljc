(ns pro.juxt.todo-test
  (:require [clojure.test :refer :all])
  (:require [pro.juxt.todo :refer :all]))

(deftest todo?-test
  (testing "Valid Todos"
    (is (= true (todo? {:todo/title  "Wash the dishes"
                        :todo/body   "I need to wash the dishes"
                        :todo/status :in-progress})))
    (is (= true (todo? {:todo/title  "Do the laundry"
                        :todo/status :todo}))))
  (testing "Invalid Todos"
    (testing "Missing title"
      (is (= false (todo? {:todo/body   "I need to wash the dishes"
                           :todo/status :in-progress}))))
    (testing "Missing status"
      (is (= false (todo? {:todo/title "Do the laundry"
                           :todo/body  "I need to wash the dishes"}))))
    (testing "Mismatching types"
      (is (= false (todo? {:todo/title  1
                           :todo/body   "I need to wash the dishes"
                           :todo/status :in-progress})))
      (is (= false (todo? {:todo/title  "Was the dishes"
                           :todo/body   10
                           :todo/status :in-progress})))
      (is (= false (todo? {:todo/title  "Was the dishes"
                           :todo/status :already-done}))))))