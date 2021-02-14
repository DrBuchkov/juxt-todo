(ns pro.juxt.http.middleware
  (:require [pro.juxt.http.auth :refer [wrap-authorization]]
            [pro.juxt.utils :refer [remove-ns-from-coll]]
            [muuntaja.middleware :as middleware]
            [ring.util.http-response :refer [not-found bad-request unauthorized internal-server-error]]
            [ring.middleware.params :refer [wrap-params]]
            [clojure.tools.logging :as log])
  (:import (clojure.lang ExceptionInfo)))

(defn wrap-errors [handler]
  (fn [req]
    (try
      (handler req)

      (catch ExceptionInfo e
        (log/error e)
        (let [exception-data (ex-data e)]
          (case (:pro.juxt/error-type exception-data)
            :pro.juxt/not-found (not-found exception-data)
            :pro.juxt/validation-error (bad-request exception-data)
            :pro.juxt/unauthorized (unauthorized "Unauthorized")
            (internal-server-error "Oops, something went wrong"))))

      (catch Exception e
        (log/error e)
        (internal-server-error "Oops, something went wrong")))))

(defn wrap-remove-ns [handler]
  (fn [req]
    (let [res (handler req)]
      (update res :body remove-ns-from-coll))))

(defn wrap-middleware [handler]
  (-> handler
      wrap-authorization
      wrap-params
      wrap-remove-ns
      wrap-errors
      middleware/wrap-format))
