(ns pro.juxt.db.filters-test
  (:require [clojure.test :refer :all])
  (:require [pro.juxt.db.filters :refer [filters->clauses]]))

(deftest filters->clauses-test
  (testing "Empty filters"
    (is (= [] (filters->clauses '?entity {}))))
  (testing "Simple map"
    (is (= '[[?entity :title "The usual suspects"]]
           (filters->clauses '?entity {:title "The usual suspects"})))
    (is (= '[[?entity :movie/title "The usual suspects"]
             [?entity :movie/year 1995]]
           (filters->clauses '?entity {:movie/title "The usual suspects"
                                       :movie/year  1995}))))
  (testing "Nested Map"
    (is (= '[[?entity :movie/title "The usual suspects"]
             [?entity :movie/year 1995]
             [?entity :movie/star ?star]
             [?star :star/name "Kevin Spacey"]
             [?star :star/age 60]]
           (filters->clauses '?entity {:movie/title "The usual suspects"
                                       :movie/year  1995
                                       :movie/star  {:star/name "Kevin Spacey"
                                                     :star/age  60}})))))
