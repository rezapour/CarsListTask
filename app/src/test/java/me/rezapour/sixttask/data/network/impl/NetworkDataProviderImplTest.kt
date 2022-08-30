package me.rezapour.sixttask.data.network.impl

import com.google.common.io.Resources
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import me.rezapour.mytask.util.MainCoroutineRule
import me.rezapour.sixttask.R
import me.rezapour.sixttask.data.network.NetworkDataProvider
import me.rezapour.sixttask.data.network.apiFake.RetrofitBuilderMock
import me.rezapour.sixttask.data.network.networkmapper.CarDataMapper
import me.rezapour.sixttask.model.Car
import me.rezapour.sixttask.utils.DataProviderException
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File
import java.io.InputStream
import java.net.HttpURLConnection
import org.junit.Assert.*


internal class NetworkDataProviderImplTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var mockWebServer: MockWebServer
    lateinit var dataProvider: NetworkDataProvider

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()

        dataProvider = NetworkDataProviderImpl(
            RetrofitBuilderMock.provideApiService(mockWebServer),
            CarDataMapper()
        )
    }

    @After
    fun destroy() {
        mockWebServer.shutdown()
    }

    fun response(fileName: String): String {
        val inputStreamUser: InputStream =
            File(Resources.getResource(fileName).toURI()).inputStream()
        return inputStreamUser.bufferedReader().use { it.readText() }
    }


    @Test
    fun `getCars() test Success`() {
        val car = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(response("Carlist.json"))


        mockWebServer.enqueue(car)

        runBlocking {
            val response = dataProvider.getCars()
            assertThat(response).isEqualTo(createSampleDomain())
        }
    }

    @Test
    fun `getcars throws internet connection exception when api call is faild`() {
        val car = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)

        mockWebServer.enqueue(car)

        val messageId = assertThrows(DataProviderException::class.java) {
            runBlocking {
                dataProvider.getCars()
            }
        }.messageId
        assertEquals(R.string.error_internet_connection, messageId)
    }


    @Test
    fun `getCars throws access denied when response code is 400 range`() {
        val car = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
            .setBody(response("Carlist.json"))

        mockWebServer.enqueue(car)


        val messageId = assertThrows(DataProviderException::class.java) {
            runBlocking {
                dataProvider.getCars()
            }
        }.messageId
        assertEquals(R.string.error_access_denied, messageId)
    }


    @Test
    fun `getCars throws server error when response code is 500 range`() {
        val car = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
            .setBody(response("Carlist.json"))

        mockWebServer.enqueue(car)


        val messageId = assertThrows(DataProviderException::class.java) {
            runBlocking {
                dataProvider.getCars()
            }
        }.messageId
        assertEquals(R.string.error_server_error, messageId)
    }


    @Test
    fun `getCars throws internet connection problem when response code is unknown range`() {
        val car = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_MOVED_PERM)
            .setBody(response("Carlist.json"))

        mockWebServer.enqueue(car)


        val messageId = assertThrows(DataProviderException::class.java) {
            runBlocking {
                dataProvider.getCars()
            }
        }.messageId
        assertEquals(R.string.error_internet_connection, messageId)
    }

//
//    @Test
//    fun `getCars throws error when response in null `() {
//        val car = MockResponse()
//            .setResponseCode(HttpURLConnection.HTTP_OK)
//            .setBody("")
//
//        mockWebServer.enqueue(car)
//
//
//        val messageId = assertThrows(DataProviderException::class.java) {
//            runBlocking {
//                dataProvider.getCars()
//            }
//        }.messageId
//        assertEquals(R.string.error_server_error, messageId)
//    }


    private fun createSampleDomain(): List<Car> {
        val car1 = Car(
            id = "WMWSW31030T222518",
            modelIdentifier = "mini",
            modelName = "MINI",
            name = "Vanessa",
            make = "BMW",
            group = "MINI",
            color = "Midnight black",
            series = "MINI",
            fuelType = "Diesel",
            fuelLevel = "70",
            transmission = "Manual",
            licensePlate = "M-VO0259",
            latitude = 48.134557,
            longitude = 11.576921,
            innerCleanliness = "REGULAR",
            carImageUrl = "https://cdn.sixt.io/codingtask/images/mini.png"
        )

        val car2 = Car(
            id = "WMWSU31070T077232",
            modelIdentifier = "mini",
            modelName = "MINI",
            name = "Regine",
            make = "BMW",
            group = "MINI",
            color = "Midnight black",
            series = "MINI",
            fuelType = "Petrol",
            fuelLevel = "55",
            transmission = "Manual",
            licensePlate = "M-I7425",
            latitude = 48.114988,
            longitude = 11.598359,
            innerCleanliness = "CLEAN",
            carImageUrl = "https://cdn.sixt.io/codingtask/images/mini.png"
        )
        return arrayListOf(car1, car2)
    }

}