(ns pro.juxt.http.routes
  (:require [pro.juxt.utils :refer [query-params->edn-params str->uuid]]
            [pro.juxt.db.core :refer [node]]
            [ring.util.http-response :refer [ok]]))

(defn gen-routes [endpoint create! browse fetch edit delete]
  [[(str "/" endpoint) {:get  (fn [req]
                                (let [query-params (:query-params req)
                                      {:keys [projection filters] :or {filters {}}} (query-params->edn-params query-params)]
                                  (-> (browse node projection filters)
                                      (ok))))
                        :post (fn [req]
                                (let [body (:body-params req)]
                                  (let [[id _] (create! node body)
                                        res {:id id}]
                                    (ok res))))}]

   [(str "/" endpoint "/:id") {:get    (fn [req]
                                         (let [query-params (:query-params req)
                                               {:keys [id]} (:path-params req)
                                               {:keys [projection]} (query-params->edn-params query-params)]
                                           (if projection
                                             (-> (fetch node (str->uuid id) projection)
                                                 (ok))
                                             (-> (fetch node (str->uuid id))
                                                 (ok)))))
                               :delete (fn [req]
                                         (let [{:keys [id]} (:path-params req)]
                                           (let [[id _] (delete node (str->uuid id))
                                                 res {:id id}]
                                             (ok res))))
                               :patch  (fn [req]
                                         (let [{:keys [id]} (:path-params req)
                                               body (:body-params req)]
                                           (-> (edit node (str->uuid id) body)
                                               second
                                               (ok))))}]])
