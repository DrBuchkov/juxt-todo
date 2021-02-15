(ns pro.juxt.todo.views
  (:require [pro.juxt.components.table :refer [table]]
            [pro.juxt.components.form :refer [form]]
            [pro.juxt.todo.ajax]
            [pro.juxt.entities.todo :as todo]
            [re-frame.core :as rf]
            [pro.juxt.utils :as utils]))


(defn create-view []
  [:h1 "Create TODO"
   [:div.p-16
    [form todo/Todo (fn [entity]
                      (fn [e]
                        (.preventDefault e)
                        (rf/dispatch [:todo/create @entity])))]]])

(defn list-view []
  [:div
   [:h1 "List TODO"]
   [:div.p-16
    [table todo/Todo :todos]]])
