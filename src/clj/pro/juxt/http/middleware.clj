(ns pro.juxt.http.middleware
  (:require [pro.juxt.http.auth :refer [wrap-authorization]]
            [muuntaja.middleware :as middleware]
            [ring.util.http-response :refer [not-found bad-request unauthorized internal-server-error]])
  (:import (clojure.lang ExceptionInfo)))

(defn wrap-errors [handler]
  (fn [req]
    (try
      (handler req)

      (catch ExceptionInfo e
        (let [exception-data (ex-data e)]
          (case (:pro.juxt/error-type exception-data)
            :pro.juxt/not-found (not-found exception-data)
            :pro.juxt/validation-error (bad-request exception-data)
            :pro.juxt/unauthorized (unauthorized "Unauthorized")
            (internal-server-error "Oops, something went wrong"))))

      (catch Exception _
        (internal-server-error "Oops, something went wrong")))))

(defn wrap-middleware [handler]
  (-> handler
      wrap-authorization
      wrap-errors
      middleware/wrap-format))
