(ns pro.juxt.db.todo-test
  (:require [clojure.test :refer :all]
            [pro.juxt.db.todo :refer [create! list-all fetch edit! delete!]]
            [malli.generator :as mg]
            [pro.juxt.db.with-db :refer [with-db *node*]]
            [pro.juxt.todo :refer [Todo todo?]]
            [clojure.spec.gen.alpha :as gen]
            [malli.core :as m]
            [crux.api :as crux])
  (:import (clojure.lang ExceptionInfo)))

(def todo-gen (mg/generator Todo))

(use-fixtures :each with-db)

(def todo-keys (->> Todo
                    m/children
                    (map first)
                    (into [:enum])))

(deftest create!-test
  (let [todo (gen/generate todo-gen)
        [todo-id tx] (create! *node* todo)]
    (is (not (nil? todo-id)))
    (is (not (nil? tx)))
    (is (uuid? todo-id))))

(deftest list-all-test
  (let [todo (gen/generate todo-gen)
        [todo-id tx] (create! *node* todo)]
    (crux/await-tx *node* tx)

    (testing "Listing with all keys"
      (let [todos (list-all *node*)]
        (is (not (empty? todos)))
        (is (= todos [(assoc todo :crux.db/id todo-id)]))))

    (testing "Listing with selected keys"
      (let [projection (-> todo-keys
                           (mg/sample {:size 3})
                           distinct
                           vec)
            _ (println projection)
            todos (list-all *node* projection)]
        (is (not (empty? todos)))
        (is (= todos
               [(select-keys todo projection)]))))))

(deftest fetch-test
  (let [todo (gen/generate todo-gen)
        [todo-id tx] (create! *node* todo)]
    (crux/await-tx *node* tx)

    (testing "Fetching with all keys"
      (let [todo (fetch *node* todo-id)]
        (is (= todo (assoc todo :crux.db/id todo-id)))))

    (testing "Fetching with selected keys"
      (let [projection (-> todo-keys
                           (mg/sample {:size 3})
                           distinct
                           vec)
            todo (fetch *node* todo-id projection)]
        (is (= todo (select-keys todo projection)))))))

(deftest edit!-test
  (let [todo (gen/generate todo-gen)
        [todo-id tx] (create! *node* todo)
        _ (crux/await-tx *node* tx)
        prev-todo (fetch *node* todo-id)
        update-data {:todo/title "Wash the dishes"}
        [_ tx] (edit! *node* todo-id update-data)
        _ (crux/await-tx *node* tx)
        updated-todo (fetch *node* todo-id)]
    (is (= updated-todo (merge prev-todo update-data)))))

(deftest delete!-test
  (let [todo (gen/generate todo-gen)
        [todo-id tx] (create! *node* todo)
        _ (crux/await-tx *node* tx)
        tx (delete! *node* todo-id)
        _ (crux/await-tx *node* tx)]

    (is (= nil (fetch *node* todo-id)))))
