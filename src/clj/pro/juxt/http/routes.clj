(ns pro.juxt.http.routes
  (:require [pro.juxt.utils :refer [query-params->edn-params str->uuid]]
            [pro.juxt.db.core :refer [node]]
            [ring.util.http-response :refer [ok]]))

(defn gen-routes [endpoint browse fetch]
  [[(str "/" endpoint) {:get (fn [req]
                               (let [query-params (:query-params req)
                                     {:keys [projection filters] :or {filters {}}} (query-params->edn-params query-params)]
                                 (-> (browse node projection filters)
                                     (ok))))}]

   [(str "/" endpoint "/:id") {:get (fn [req]
                                      (let [query-params (:query-params req)
                                            {:keys [id]} (:path-params req)
                                            {:keys [projection]} (query-params->edn-params query-params)]
                                        (if projection
                                          (-> (fetch node (str->uuid id) projection)
                                              (ok))
                                          (-> (fetch node (str->uuid id))
                                              (ok)))))}]])