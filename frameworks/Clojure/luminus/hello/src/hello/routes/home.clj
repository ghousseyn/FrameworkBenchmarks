(ns hello.routes.home
  (:require [hello.layout :as layout]
            [hello.db.core :as db]
            [compojure.core :refer [defroutes GET]]
            [ring.util.response :refer [response content-type]]
            [clojure.java.io :as io]))

(defn json-serialization
  "Test 1: JSON serialization"
  []
  (response {:message "Hello, World!"}))

(defn single-query-test
  "Test 2: Single database query"
  []
  (-> 1 db/run-queries first response))

(defn multiple-query-test
  "Test 3: Multiple database query"
  [queries]
  (-> queries
      db/run-queries
      response))

(defn fortunes
  "Test 4: Fortunes"
  []
  (layout/render "home.html" {:messages (db/get-fortunes)}))

(defn db-update
  "Test 5: Database updates"
  [queries]
  (-> queries
      db/update-and-persist
      response))

(def plaintext
  "Test 6: Plaintext"
  (->
    (response "Hello, World!")
    (content-type "text/plain")))


(defroutes home-routes
  (GET "/"                 [] "Hello, World!")
  (GET "/plaintext"        [] plaintext)
  (GET "/json"             [] (json-serialization))
  (GET "/db"               [] (single-query-test))
  (GET "/queries/"         [] (multiple-query-test 1))
  (GET "/queries/:queries" [queries] (multiple-query-test queries))
  (GET "/fortunes"         [] (fortunes))
  (GET "/updates/"         [] (db-update 1))
  (GET "/updates/:queries" [queries] (db-update queries)))
