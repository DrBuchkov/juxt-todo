(ns pro.juxt.seed
  (:require [pro.juxt.db.core :refer [node]]
            [pro.juxt.db.todo :as todo]))


(defn seed-db! []
  (let [todos [{:todo/title       "Wash the dishes"
                :todo/description "I have to wash the dishes"
                :todo/status      :todo}
               {:todo/title       "Do the laundry"
                :todo/description "I have to put my clothes for laundry and then dry them"
                :todo/status      :in-progress}]]
    (doseq [item todos]
      (todo/create! node item))))
