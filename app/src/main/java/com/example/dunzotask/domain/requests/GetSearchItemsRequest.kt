package com.example.dunzotask.domain.requests

class GetSearchItemsRequest(private val pageNumber: Int = 1, private val searchTerm: String) {

    fun getParams(): Map<String, String> {
        val params = HashMap<String, String>()
        params["page"] = pageNumber.toString()
        params["text"] = searchTerm
        params["nojsoncallback"] = "1"
        params["format"] = "json"
        params["api_key"] = "062a6c0c49e4de1d78497d13a7dbb360"
        params["method"] = "flickr.photos.search"
        params["per_page"] = "4"
        return params;
    }
}