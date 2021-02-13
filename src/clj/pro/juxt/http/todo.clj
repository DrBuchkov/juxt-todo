(ns pro.juxt.http.todo
  (:require [pro.juxt.db.core :refer [node]]
            [pro.juxt.db.todo :as todo]
            [pro.juxt.utils :refer [query-params->edn-params str->uuid]]
            [ring.util.http-response :refer [ok]]
            [clojure.tools.logging :as log]))


(defn todo-routes []
  [["/todo" {:get (fn [req]
                    (let [query-params (:query-params req)
                          {:keys [projection filters] :or {filters {}}} (query-params->edn-params query-params)]
                      (-> (todo/browse node projection filters)
                          (ok))))}]

   ["/todo/:id" {:get (fn [req]
                        (let [query-params (:query-params req)
                              {:keys [id]} (:path-params req)
                              {:keys [projection]} (query-params->edn-params query-params)]
                          (if projection
                            (-> (todo/fetch node (str->uuid id) projection)
                                (ok))
                            (-> (todo/fetch node (str->uuid id))
                                (ok)))))}]])