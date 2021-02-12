(ns pro.juxt.db.filters
  (:require [clojure.core.match :refer [match]]))

(defn filters->clauses [entity-sym filters]
  (match filters
         (v :guard map?) (->> v
                              (map (partial filters->clauses entity-sym))
                              (reduce (fn [acc clause-or-clauses]
                                        (if (vector? (first clause-or-clauses))
                                          (into acc clause-or-clauses)
                                          (conj acc clause-or-clauses)))
                                      []))
         [(k :guard keyword?) (v :guard map?)] (let [child-sym (symbol (str "?" (name k)))]
                                                 (into [[entity-sym k child-sym]]
                                                       (filters->clauses child-sym v)))
         [(k :guard keyword?) v] [entity-sym k v]))
