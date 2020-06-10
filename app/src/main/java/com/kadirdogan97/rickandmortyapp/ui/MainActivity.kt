package com.kadirdogan97.rickandmortyapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.kadirdogan97.rickandmortyapp.FeedResultsQuery
import com.kadirdogan97.rickandmortyapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apolloClient = ApolloClient.builder()
                .serverUrl("https://rickandmortyapi.com/graphql/")
                .build()

        apolloClient.query(FeedResultsQuery(1, "Rick","","","",""))
                .enqueue(object : ApolloCall.Callback<FeedResultsQuery.Data>() {
                    override fun onFailure(e: ApolloException) {
                        Log.e("Apollo", "Error", e)
                    }

                    override fun onResponse(response: Response<FeedResultsQuery.Data>) {
                        Log.e("Apollo", "Launch site: ${response.data?.characters.toString()}")
                    }
                })

    }
}