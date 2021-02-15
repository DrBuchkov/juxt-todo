(ns pro.juxt.todo.views
  (:require [pro.juxt.components.table :refer [table]]
            [pro.juxt.components.form :refer [form]]
            [pro.juxt.todo.ajax]
            [pro.juxt.entities.todo :as todo]
            [re-frame.core :as rf]
            [cljs-uuid-utils.core :as uuid]
            [reagent.core :as r]
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
   [:h1 "List TODOs"]
   [:div.p-16
    [table todo/Todo :todos {:delete (fn [entity]
                                       [:span.inline-flex.justify-center.items-center.ml-4
                                        [:i.fa.fa-times.fa-lg.cursor-pointer
                                         {:title    "Delete"
                                          :on-click (fn [_]
                                                      (rf/dispatch [:todo/delete (-> entity
                                                                                     :crux.db/id
                                                                                     uuid/uuid-string)]))}]])
                             :edit   (fn [entity]
                                       [:span.inline-flex.justify-center.items-center.ml-4
                                        [:i.fa.fa-pencil.fa-lg.cursor-pointer
                                         {:title    "Delete"
                                          :on-click (fn [_]
                                                      (utils/redirect-page!
                                                        (str "#/todo/edit/" (-> entity
                                                                                :crux.db/id
                                                                                uuid/uuid-string))))}]])}]]])

(defn edit-view []
  (let [{:keys [parameters] :as route} @(rf/subscribe [:nav/route])
        {:keys [path]} parameters
        {:keys [id]} path
        todo @(rf/subscribe [:todo])]
    [:h1 "Edit TODO"
     [:div.p-16
      [form todo/Todo (fn [entity]
                        (fn [e]
                          (.preventDefault e)
                          (rf/dispatch [:todo/edit id @entity])))
       "Save Changes"
       todo]]]))
