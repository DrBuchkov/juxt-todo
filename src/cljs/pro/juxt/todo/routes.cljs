(ns pro.juxt.todo.routes
  (:require [pro.juxt.todo.views :as todo-views]
            [re-frame.core :as rf]))

(defn routes []
  [["/todo/create" {:name :todo/create
                    :view #'todo-views/create-view}]
   ["/todo/list" {:name        :todo/list
                  :view        #'todo-views/list-view
                  :controllers [{:start (fn [e]
                                          (rf/dispatch [:todo/browse]))}]}]
   ["/todo/edit/:id" {:name        :todo/edit
                      :view        #'todo-views/edit-view
                      :parameters  {:path {:id string?}}
                      :controllers [{:parameters {:path [:id]}
                                     :start      (fn [params]
                                                   (let [id (-> params :path :id)]
                                                     (rf/dispatch [:todo/fetch id])))}]}]])
