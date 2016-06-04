# elasticsearch-oomonster

This plugin adds a `/_cat/oom` endpoint that will helpfully cause your Elasticsearch to eat up all available heap and then explode in a ball of flame.

Remember, the OOMonster knows when you're sleeping. Probably best not to sleep.

```sh
mvn clean package
/path/to/elasticsearch/bin/plugin install `pwd`/target/releases/oomonster-2.3.3.zip
<restart elasticsearch>
curl localhost:9200/_cat/oom
```
