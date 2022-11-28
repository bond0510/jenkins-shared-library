import com.ec.Configuration

def call ( String pipelinePath ) {
    new Configuration(pipelinePath).dockerConfig()
}
