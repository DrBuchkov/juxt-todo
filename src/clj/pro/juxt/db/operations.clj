(ns pro.juxt.db.operations
  (:require [pro.juxt.utils :as utils]
            [malli.core :as m]
            [malli.util :as mu]
            [malli.error :as me]
            [crux.api :as crux]
            [pro.juxt.db.filters :refer [filters->clauses]]))

(defn find-first-req-key [spec]
  (->> spec
       m/children
       (filter (fn [[_ opts _]]
                 (not (:optional opts))))
       first
       first))

(defn create!-fn [spec]
  (fn [node entity]
    (when-not (m/validate spec entity)
      (throw (ex-info "Invalid TODO"
                      {:pro.juxt/error-type :pro.juxt/validation-error
                       :pro.juxt/error-data (-> (m/explain spec entity)
                                                (me/humanize))})))
    (let [todo-id (utils/gen-uuid)]
      [todo-id (crux/submit-tx node [[:crux.tx/put (assoc entity :crux.db/id todo-id)]])])))

(defn browse-fn [spec]
  (fn browse
    ([node]
     (browse node nil))
    ([node projection]
     (browse node projection {}))
    ([node projection filters]
     (let [projection (when projection (into [:crux.db/id] projection))
           unique-kw (find-first-req-key spec)
           clauses (into `[[?entity ~unique-kw]] (filters->clauses `?entity filters))
           res (crux/q (crux/db node)
                       `{:find  [(eql/project ?entity ~(or projection '[*]))]
                         :where ~clauses})]
       (-> res seq flatten)))))

(defn fetch-fn []
  (fn
    ([node entity-id]
     (crux/entity (crux/db node) entity-id))
    ([node entity-id projection]
     (crux/project (crux/db node) projection entity-id))))

(defn edit!-fn [spec fetch]
  (fn [node entity-id entity]
    (when-not (m/validate (mu/optional-keys spec) entity)
      (throw (ex-info "Given data does not conform entity's specification."
                      {:pro.juxt/error-type :pro.juxt/validation-error
                       :pro.juxt/error-data (-> (m/explain (mu/optional-keys spec) entity)
                                                (me/humanize))})))
    (let [prev-value (fetch node entity-id)
          _ (when-not prev-value
              (throw (ex-info "No entity with such id to edit."
                              {:pro.juxt/error-type :pro.juxt/not-found
                               :pro.juxt/error-data {:id entity-id}})))
          updated-value (merge prev-value entity)]
      [entity-id updated-value (crux/submit-tx node [[:crux.tx/put updated-value]])])))

(defn delete!-fn [fetch]
  (fn [node entity-id]
    (let [todo (fetch node entity-id [:crux.db/id])]
      (when (empty? todo)
        (throw (ex-info "No entity with such id to delete." {:pro.juxt/error-type :pro.juxt/not-found
                                                             :pro.juxt/error-data {:id entity-id}})))
      [entity-id (crux/submit-tx node [[:crux.tx/delete entity-id]])])))