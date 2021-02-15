(ns pro.juxt.todo.views
  (:require [pro.juxt.components.table :refer [table]]
            [pro.juxt.components.form :refer [form]]
            [pro.juxt.todo.http]
            [pro.juxt.entities.todo :as todo]))


(defn create-view []
  [:h1 "Create TODO"
   [:div.p-6
    [form todo/Todo :todo/create]]])

(defn list-view []
  [:div
   [:h1 "List TODO"]
   [table]])
