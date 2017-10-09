package org.hedgehog.testapp.rest.models

/**
 * Created by hedgehog on 06.10.17.
 */
class PostResponse(val userId: Int,
                   val id: Int,
                   var title: String,
                   var body: String)

class CommentResponse(val userId: Int,
                      val id: Int,
                      var name: String,
                      var email: String,
                      var body: String)

class UsersResponse(val id: Int,
                    var name: String,
                    var username: String,
                    var email: String,
                    var address: Address,
                    var phone: String,
                    var website: String,
                    var company: Company) {

    inner class Address(var street: String,
                        var suite: String,
                        var city: String,
                        var zipcode: String,
                        var geo: Geo) {

        override fun toString(): String {
            return "Адрес: $city, $suite, $street, $zipcode, (${geo.lat}, ${geo.lng})"
        }

        inner class Geo(var lat: String,
                        var lng: String) {
            override fun toString(): String {
                return "$lat, $lng"
            }
        }
    }

    inner class Company(var name: String,
                        var catchPhrase: String,
                        var bs: String) {
        override fun toString(): String {
            return "Компания: $name"
        }
    }

    override fun toString(): String {
        return "$name, $username, $email, $address"
    }
}


class PhotoResponse(val albumId: Int,
                    val id: Int,
                    var title: String,
                    var url: String,
                    var thumbnailUrl: String)

class ToDosResponse(val userId: Int,
                    val id: Int,
                    var title: String,
                    var completed: Boolean)