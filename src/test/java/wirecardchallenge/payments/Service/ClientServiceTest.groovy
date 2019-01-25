package wirecardchallenge.payments.Service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Shared
import spock.lang.Specification
import wirecardchallenge.payments.model.Client
import wirecardchallenge.payments.repository.ClientRepository

import static wirecardchallenge.WirecardChallengeExceptions.clientNotFoundException
import static wirecardchallenge.WirecardChallengeExceptions.idRequired

@DataJpaTest
@SpringBootTest
class ClientServiceTest extends Specification {
    @Autowired
    ClientService service

    @Autowired
    ClientRepository repository

    @Shared
    Client client

    def setupSpec() {
        client = new Client()
        client.firstName = "test"
    }

    def "given known id should find client"() {
        given:
        def saved = repository.save(client)

        when:
        def found = service.findById(saved.id)

        then:
        found.id == saved.id
    }

    def "given unknown id should not find client by id"() {
        given:

        when:
        service.findById(123)

        then:
        thrown clientNotFoundException
    }

    def "when find by id without id should throw error"() {
        given:

        when:
        service.findById(123)

        then:
        thrown idRequired
    }
}
