/*
 * This file is part of MastodonX.
 *
 * MastodonX is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * MastodonX is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with MastodonX. If not, see <https://www.gnu.org/licenses/>.
 */
package social.androiddev.common.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.get
import kotlinx.serialization.SerializationException
import social.androiddev.common.network.model.Instance

class MastodonApiImpl(
    private val httpClient: HttpClient,

) : MastodonApi {
    override suspend fun getInstance(domain: String?): Result<Instance> {
        return try {
            Result.success(
                httpClient.get("/api/v1/instance") {
                    domain?.let {
                        headers.append("domain", domain)
                    }
                }.body()
            )
        } catch (exception: SerializationException) {
            Result.failure(exception = exception)
        } catch (exception: ResponseException) {
            Result.failure(exception = exception)
        }
    }
}
