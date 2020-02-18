# Database Optimization

## What does this app do?
It is an app which downloads 18MB dictionary that is basically 26 html files one for each word. Each file contains words, their figure of speech and dictionary meaning. App downlaods all the files and store it in a Room database locally in minimum execution time.

Minimum Execution Time Acheived - 130 seconds

## Approach

### Jsoup
Jsoup is a Java library for working with real-world HTML. It provides a very convenient API for extracting and manipulating data, using the best of DOM, CSS, and jquery-like methods.
jsoup implements the WHATWG HTML5 specification, and parses HTML to the same DOM as modern browsers do.

- scrape and parse HTML from a URL, file, or string
- find and extract data, using DOM traversal or CSS selectors
- manipulate the HTML elements, attributes, and text
- clean user-submitted content against a safe white-list, to prevent XSS attacks 
- output tidy HTML

jsoup is designed to deal with all varieties of HTML found in the wild; from pristine and validating, to invalid tag-soup; jsoup will create a sensible parse tree.

To use Jsoup in your app, add the following dependencies to your app's build.gradle file:
```
implementation 'org.jsoup:jsoup:1.12.2'
```

Whole document is downloaded with the connect funtion.Example:
```
Document doc = Jsoup.connect("http://jsoup.org").get();
```

Used the Element.select(String selector) and Elements.select(String selector) methods for extrcting the words definition:
```
Elements elements = doc.select("p");
```

### Room Database
Room provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite.
Apps that handle non-trivial amounts of structured data can benefit greatly from persisting that data locally. The most common use case is to cache relevant pieces of data. That way, when the device cannot access the network, the user can still browse that content while they are offline. Any user-initiated content changes are then synced to the server after the device is back online.

To use Room in your app, add the following dependencies to your app's build.gradle file:
```
implementation 'android.arch.persistence.room:runtime:1.1.1'
annotationProcessor 'android.arch.persistence.room:compiler:1.1.1'
```

Datbase Access Interface:
```
@Dao
public interface WordDao {

    @Query("SELECT * FROM word")
    List<Word> getAll();

    @Query("SELECT COUNT(*) from word")
    int countWords();

    @Insert
    void insertAll(Word... words);

    @Delete
    void delete(Word word);
}
```
