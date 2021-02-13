(ns pro.juxt.http.todo
  (:require [pro.juxt.db.core :refer [node]]
            [pro.juxt.db.todo :as todo]
            [pro.juxt.http.routes :refer [gen-routes]]
            [pro.juxt.utils :refer [query-params->edn-params str->uuid]]
            [ring.util.http-response :refer [ok]]))


(defn todo-routes [] (gen-routes "todo" todo/browse todo/fetch))