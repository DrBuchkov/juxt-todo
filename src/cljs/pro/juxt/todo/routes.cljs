(ns pro.juxt.todo.routes
  (:require [pro.juxt.todo.views :as todo-views]
            [re-frame.core :as rf]))

(defn routes []
  [["/todo/create" {:name :todo/create
                    :view #'todo-views/create-view}]
   ["/todo/list" {:name        :todo/list
                  :view        #'todo-views/list-view
                  :controllers [{:start (fn [_]
                                          (rf/dispatch [:todo/browse]))}]}]])
