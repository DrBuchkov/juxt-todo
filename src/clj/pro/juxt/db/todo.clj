(ns pro.juxt.db.todo
  (:require [pro.juxt.utils :as utils]
            [pro.juxt.entities.todo :as todo]
            [malli.core :as m]
            [malli.util :as mu]
            [crux.api :as crux]
            [pro.juxt.db.filters :refer [filters->clauses]]))

(defn create! [node todo]
  (when-not (todo/todo? todo)
    (throw (ex-info "Invalid TODO"
                    {:pro.juxt/error-type :pro.juxt/validation-error
                     :pro.juxt/error-data (m/explain todo/Todo todo)})))
  (let [todo-id (utils/gen-uuid)]
    [todo-id (crux/submit-tx node [[:crux.tx/put (assoc todo :crux.db/id todo-id)]])]))

(defn browse
  ([node]
   (browse node nil))
  ([node projection]
   (browse node projection {}))
  ([node projection filters]
   (let [clauses (into `[[?entity :todo/title]] (filters->clauses `?entity filters))
         res (crux/q (crux/db node)
                     `{:find  [(eql/project ?entity ~(or projection '[*]))]
                       :where ~clauses})]
     (-> res seq flatten))))

(defn fetch
  ([node todo-id]
   (crux/entity (crux/db node) todo-id))
  ([node todo-id projection]
   (crux/project (crux/db node) projection todo-id)))

(defn edit! [node todo-id todo]
  (when-not (m/validate (mu/optional-keys todo/Todo) todo)
    (throw (ex-info "Invalid Todo"
                    {:pro.juxt/error-type :pro.juxt/validation-error
                     :pro.juxt/error-data (m/explain (mu/optional-keys todo/Todo) todo)})))
  (let [prev-value (fetch node todo-id)
        _ (when-not prev-value
            (throw (ex-info "No entity with such id to edit."
                            {:pro.juxt/error-type :pro.juxt/not-found
                             :pro.juxt/error-data {:id todo-id}})))
        updated-value (merge prev-value todo)]
    [todo-id (crux/submit-tx node [[:crux.tx/put updated-value]])]))

(defn delete! [node todo-id]
  (let [todo (fetch node todo-id [:crux.db/id])]
    (when (empty? todo)
      (throw (ex-info "No entity with such id to delete." {:pro.juxt/error-type :pro.juxt/not-found
                                                           :pro.juxt/error-data {:id todo-id}})))
    (crux/submit-tx node [[:crux.tx/delete todo-id]])))