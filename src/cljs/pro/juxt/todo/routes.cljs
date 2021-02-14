(ns pro.juxt.todo.routes
  (:require [pro.juxt.todo.views :as todo-views]))

(defn routes []
  [["/todo/create" {:name :todo/create
                    :view #'todo-views/create-view}]
   ["/todo/list" {:name :todo/list
                  :view #'todo-views/list-view}]])
