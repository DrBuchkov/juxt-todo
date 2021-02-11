(ns pro.juxt.utils-test
  (:require [clojure.test :refer :all])
  (:require [pro.juxt.utils :refer [gen-uuid]]))

(deftest gen-uuid-test
  (is (= true (uuid? (gen-uuid)))))
