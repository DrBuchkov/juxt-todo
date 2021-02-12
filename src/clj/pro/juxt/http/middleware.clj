(ns pro.juxt.http.middleware
  (:require [muuntaja.middleware :as middleware]
            [ring.util.http-response :refer [not-found bad-request internal-server-error]])
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
            (internal-server-error "Oops, something went wrong"))))

      (catch Exception _
        (internal-server-error "Oops, something went wrong")))))

(defn wrap-middleware [handler]
  (-> handler
      wrap-errors
      middleware/wrap-format))
