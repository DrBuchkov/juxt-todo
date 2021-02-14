(ns pro.juxt.todo.views
  (:require [pro.juxt.components.table :refer [table]]))


(defn create-view []
  [:h1 "Create TODO"])

(defn list-view []
  [:div
   [:h1 "List TODO"]
   [table]])
