package maw.data.twitter;

import javax.swing.JOptionPane;

import twitter4j.AccountSettings;
import twitter4j.AccountTotals;
import twitter4j.AsyncTwitter;
import twitter4j.AsyncTwitterFactory;
import twitter4j.Category;
import twitter4j.DirectMessage;
import twitter4j.Friendship;
import twitter4j.IDs;
import twitter4j.Location;
import twitter4j.PagableResponseList;
import twitter4j.Place;
import twitter4j.ProfileImage;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.RelatedResults;
import twitter4j.Relationship;
import twitter4j.ResponseList;
import twitter4j.SimilarPlaces;
import twitter4j.Status;
import twitter4j.Trends;
import twitter4j.TwitterAPIConfiguration;
import twitter4j.TwitterException;
import twitter4j.TwitterListener;
import twitter4j.TwitterMethod;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.api.HelpMethods.Language;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationContext;

import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxObject;

/*
 * This object combines searching and 
 */

public class client extends MaxObject implements TwitterListener {

    MaxObject dialog;

    boolean debug = true;

    /*
    AsyncTwitterFactory factory = new AsyncTwitterFactory();

    AsyncTwitter twitterClient = null;

    */
    String username = null;

    String twitterClientVersion = "1.0";
    String twitterClientURL = "http://dev.minneapolisartonwheels.org/maw.data.twitter.xml";
    String twitterUserAgent = "maw.data.twitter /1.0";
    String twitterSource = "maw.data.twitter";

    String consumerKey = "UNDEFINED";
    String consumerSecret = "UNDEFINED";
    String accessToken = "UNDEFINED";
    String accessTokenSecret = "UNDEFINED";
    
    
    // queries
    String query = "MAW";
    String lang = "";
    int resultsperquery = 100;
    long lastid = -1;

    Thread authorizationThread = null;

    
    public client() {

        post("Twitter version!");

        declareAttribute("debug");

        // poster
        declareAttribute("username");
        declareAttribute("accesstoken");

        // query
        declareAttribute("query");
        declareAttribute("lang");
        declareAttribute("resultsperquery");
        declareAttribute("lastid");

        // max stuff
        declareInlets(new int[] { DataTypes.LIST });
        declareOutlets(new int[] { DataTypes.LIST });

        setInletAssist(new String[] { "input" });
        setOutletAssist(new String[] { "Twitter output" });

        // twitterClient = factory.getInstance("xxx", "xxx", this);

        // twitterClient = new AsyncTwitter(null, null, this);

    }

    public void cancelauthorization() {
        if (authorizationThread != null || authorizationThread.isAlive()) {
            authorizationThread.interrupt();
        }

        
    }
    
    public void authorize() {

        Configuration conf = ConfigurationContext.getInstance();
        
       
        // AsyncTwitter twitter = new AsyncTwitterFactory(this).getInstance();
//                    
//                   // twitter.setOAuthConsumer(oAuthToken, oAuthSecretToken);
//
//                 //   setOAuthConsumer(java.lang.String consumerKey, java.lang.String consumerSecret) 
//                    
//                    RequestToken requestToken = null;
//
//                    try {
//                        post("REQUESTING TOKEN");
//                        requestToken = twitter.getOAuthRequestToken();
//                        
//                      
//                        
//                    } catch (TwitterException e) {
//                        error("Unable to get authorization URL :" + e.getStatusCode() + " error.");
//                        e.printStackTrace();
//                        return;
//                    }
//
//                    post("in here!!");
//
//                    AccessToken accessToken = null;
//
//                    String authorizationUrl = (String) JOptionPane.showInputDialog(null,
//                            "Paste this link into a web browser:", "OAuth Dialog",
//                            JOptionPane.PLAIN_MESSAGE, null, null,
//                            requestToken.getAuthorizationURL());
//
//                    if (authorizationUrl == null) {
//                        error("Authorization cancelled.");
//                        return;
//                    }
//
//                    String pin = (String) JOptionPane.showInputDialog(null,
//                            "Type in the PIN Number proved by Twitter:", "OAuth Pin",
//                            JOptionPane.PLAIN_MESSAGE, null, null, "");
//
//                    if (pin == null) {
//                        error("Authorization cancelled.");
//                        return;
//                    }
//
//                    // If a string was returned, say so.
//                    if (pin.length() > 0) {
//                        try {
//                            accessToken = twitter.getOAuthAccessToken(requestToken, pin);
//                        } catch (TwitterException e) {
//                            error("Unable to get authorization URL :" + e.getStatusCode()
//                                    + " error.");
//                            e.printStackTrace();
//                            return;
//                        }
//
//                        System.out.println("accessToken=" + accessToken);
//
//                        User user = null;
//                        try {
//                            user = twitter.verifyCredentials();
//                        } catch (TwitterException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//
//                        System.out.println("userid=" + user.getId());
//                        System.out.println("name=" + user.getName());
//                        System.out.println("token=" + accessToken.getToken());
//                        System.out.println("token secret=" + accessToken.getTokenSecret());
//
//                        // setLabel("Green eggs and... " + s + "!");
//                        return;
//                    }
//
//                }
          
    }


boolean hasConsumerKey() {
    return consumerKey != null && consumerKey.compareToIgnoreCase("UNDEFINED") != 0;
}

boolean hasConsumerSecret() {
    return consumerSecret != null && consumerSecret.compareToIgnoreCase("UNDEFINED") != 0;
    
}
boolean hasAccessToken() {
    return accessToken != null && accessToken.compareToIgnoreCase("UNDEFINED") != 0;

}
boolean hasAccessTokenSecret() {
    return accessTokenSecret != null && accessTokenSecret.compareToIgnoreCase("UNDEFINED") != 0;

}


    /*
    // twitter client

    // to connect
    public void connect() {
        if (username == null) {
            error("Please set your Twitter username.");
            return;
        } else if (password == null) {
            error("Please set your Twitter password.");
            return;
        } else {

            twitterClient.shutdown(); // shutdown first

           // twitterClient = new AsyncTwitter(username, password, this);

            // twitterClient.setClientVersion(twitterClientVersion);
            // twitterClient.setClientURL(twitterClientURL);
            // twitterClient.setSource(twitterSource);
            // twitterClient.setUserAgent(twitterUserAgent);
        }
    }
    */

    /*
    // to disconnect
    public void disconnect() {
        twitterClient.shutdown();
    }

    // to twit a message
    public void updatestatus(String twit) {
        if (twitterClient != null) {
            twitterClient.updateStatus(twit);
        } else {
            error("Please connect to Twitter.");
        }
    }

    public void updateprofile(String name, String email, String url, String location,
            String description) {
        twitterClient.updateProfile(name, email, url, location, description);

    }

    // do search
    public void search() {
        if (twitterClient != null) {
            Query q = new Query();
            q.setLang(this.lang);
            q.setQuery(this.query);
            q.setRpp(this.resultsperquery);
            q.setSinceId(this.lastid);
            twitterClient.search(q);
        } else {
            error("Please connect to Twitter.");
        }

    }

    public void directmessage(Atom[] input) {
        if (twitterClient != null) {
            twitterClient.sendDirectMessage(input[0].getString(), input[1].getString());
        } else {
            error("Please connect to Twitter.");
        }
    }
    */

    /*
     * // twitter search public void start() { if (!tl.isRunning()) { tl.startSearch(); } else {
     * error("Twitter listener is already running."); } } public void stop() { if (tl.isRunning()) {
     * if (debug) post("Twitter listener stoping."); tl.stopSearch(); } else {
     * error("Twitter listener is not running."); } }
     */

    /*
     * public void registerDispose(Disposable o) { // nothing to do uses notify delete }
     */

    /*
     * public void twitterListenerState(int i) { if (debug) post("State=" + tl.getStateString(i));
     * // outlet(this.getNumOutlets() + 1, new Atom[] { Atom.newAtom("status"), //
     * Atom.newAtom(tl.getStateString(i)) }); }
     */

    /*
     * public void newTwitterMessage(TwitterListener listener) { if (debug) post("got a listener " +
     * listener.getName() + "   " + listener.getNewTwitters().size() + " new TWTIS");
     * ArrayList<Twit> twits = listener.getNewTwitters(); Iterator<Twit> twitIterator =
     * twits.iterator(); while (twitIterator.hasNext()) { Twit twit = twitIterator.next(); if
     * (debug) System.out.println(twit); Atom[] outputArray = new Atom[] {
     * Atom.newAtom(twit.getId()), Atom.newAtom(twit.getUsername()), Atom.newAtom(twit.getAuthor()),
     * Atom.newAtom(twit.getMessage()), Atom.newAtom(twit.getPublished().toString()),
     * Atom.newAtom(twit.getUpdated().toString()) }; outlet(0, outputArray); } }
     */

    protected void notifyDeleted() { // clean up!
        // if (twitterClient != null) {
        // twitterClient.shutdown();
        // }
    }

    public void addedUserListMember(UserList arg0) {
        // TODO Auto-generated method stub
        
    }

    public void addedUserListMembers(UserList arg0) {
        // TODO Auto-generated method stub
        
    }

    public void checkedUserListMembership(User arg0) {
        // TODO Auto-generated method stub
        
    }

    public void checkedUserListSubscription(User arg0) {
        // TODO Auto-generated method stub
        
    }

    public void createdBlock(User arg0) {
        // TODO Auto-generated method stub
        
    }

    public void createdFavorite(Status arg0) {
        // TODO Auto-generated method stub
        
    }

    public void createdFriendship(User arg0) {
        // TODO Auto-generated method stub
        
    }

    public void createdPlace(Place arg0) {
        // TODO Auto-generated method stub
        
    }

    public void createdUserList(UserList arg0) {
        // TODO Auto-generated method stub
        
    }

    public void deletedUserListMember(UserList arg0) {
        // TODO Auto-generated method stub
        
    }

    public void destroyedBlock(User arg0) {
        // TODO Auto-generated method stub
        
    }

    public void destroyedDirectMessage(DirectMessage arg0) {
        // TODO Auto-generated method stub
        
    }

    public void destroyedFavorite(Status arg0) {
        // TODO Auto-generated method stub
        
    }

    public void destroyedFriendship(User arg0) {
        // TODO Auto-generated method stub
        
    }

    public void destroyedStatus(Status arg0) {
        // TODO Auto-generated method stub
        
    }

    public void destroyedUserList(UserList arg0) {
        // TODO Auto-generated method stub
        
    }

    public void disabledNotification(User arg0) {
        // TODO Auto-generated method stub
        
    }

    public void enabledNotification(User arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotAPIConfiguration(TwitterAPIConfiguration arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotAccountSettings(AccountSettings arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotAccountTotals(AccountTotals arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotAllUserLists(ResponseList<UserList> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotAvailableTrends(ResponseList<Location> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotBlockingUsers(ResponseList<User> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotBlockingUsersIDs(IDs arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotCurrentTrends(Trends arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotDailyTrends(ResponseList<Trends> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotDirectMessage(DirectMessage arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotDirectMessages(ResponseList<DirectMessage> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotExistsBlock(boolean arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotExistsFriendship(boolean arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotFavorites(ResponseList<Status> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotFollowersIDs(IDs arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotFriendsIDs(IDs arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotGeoDetails(Place arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotHomeTimeline(ResponseList<Status> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotIncomingFriendships(IDs arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotLanguages(ResponseList<Language> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotLocationTrends(Trends arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotMemberSuggestions(ResponseList<User> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotMentions(ResponseList<Status> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotNoRetweetIds(IDs arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotOutgoingFriendships(IDs arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotPrivacyPolicy(String arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotProfileImage(ProfileImage arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotRateLimitStatus(RateLimitStatus arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotRelatedResults(RelatedResults arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotRetweetedBy(ResponseList<User> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotRetweetedByIDs(IDs arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotRetweetedByMe(ResponseList<Status> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotRetweetedByUser(ResponseList<Status> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotRetweetedToMe(ResponseList<Status> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotRetweetedToUser(ResponseList<Status> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotRetweets(ResponseList<Status> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotRetweetsOfMe(ResponseList<Status> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotReverseGeoCode(ResponseList<Place> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotSentDirectMessages(ResponseList<DirectMessage> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotShowFriendship(Relationship arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotShowStatus(Status arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotShowUserList(UserList arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotSimilarPlaces(SimilarPlaces arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotSuggestedUserCategories(ResponseList<Category> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotTermsOfService(String arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotUserDetail(User arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotUserListMembers(PagableResponseList<User> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotUserListMemberships(PagableResponseList<UserList> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotUserListStatuses(ResponseList<Status> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotUserListSubscribers(PagableResponseList<User> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotUserListSubscriptions(PagableResponseList<UserList> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotUserLists(PagableResponseList<UserList> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotUserSuggestions(ResponseList<User> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotUserTimeline(ResponseList<Status> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void gotWeeklyTrends(ResponseList<Trends> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void lookedUpFriendships(ResponseList<Friendship> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void lookedupUsers(ResponseList<User> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void onException(TwitterException arg0, TwitterMethod arg1) {
        // TODO Auto-generated method stub
        
    }

    public void reportedSpam(User arg0) {
        // TODO Auto-generated method stub
        
    }

    public void retweetedStatus(Status arg0) {
        // TODO Auto-generated method stub
        
    }

    public void searched(QueryResult arg0) {
        // TODO Auto-generated method stub
        
    }

    public void searchedPlaces(ResponseList<Place> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void searchedUser(ResponseList<User> arg0) {
        // TODO Auto-generated method stub
        
    }

    public void sentDirectMessage(DirectMessage arg0) {
        // TODO Auto-generated method stub
        
    }

    public void subscribedUserList(UserList arg0) {
        // TODO Auto-generated method stub
        
    }

    public void tested(boolean arg0) {
        // TODO Auto-generated method stub
        
    }

    public void unsubscribedUserList(UserList arg0) {
        // TODO Auto-generated method stub
        
    }

    public void updatedAccountSettings(AccountSettings arg0) {
        // TODO Auto-generated method stub
        
    }

    public void updatedFriendship(Relationship arg0) {
        // TODO Auto-generated method stub
        
    }

    public void updatedProfile(User arg0) {
        // TODO Auto-generated method stub
        
    }

    public void updatedProfileBackgroundImage(User arg0) {
        // TODO Auto-generated method stub
        
    }

    public void updatedProfileColors(User arg0) {
        // TODO Auto-generated method stub
        
    }

    public void updatedProfileImage(User arg0) {
        // TODO Auto-generated method stub
        
    }

    public void updatedStatus(Status arg0) {
        // TODO Auto-generated method stub
        
    }

    public void updatedUserList(UserList arg0) {
        // TODO Auto-generated method stub
        
    }

    public void verifiedCredentials(User arg0) {
        // TODO Auto-generated method stub
        
    }



    /*

    public void searched(QueryResult results) {

        List<Tweet> tweets = results.getTweets();
        Iterator<Tweet> tweetIterator = tweets.iterator();
        lastid = Math.max(results.getMaxId(), lastid);

        while (tweetIterator.hasNext()) {
            Tweet t = tweetIterator.next();

            Atom[] outputArray = new Atom[] { Atom.newAtom(Long.toString(t.getId())),
                    Atom.newAtom(t.getFromUserId()), Atom.newAtom(t.getFromUser()),
                    Atom.newAtom(t.getText()), Atom.newAtom(Long.toString(t.getCreatedAt().getTime()))};

            outlet(0, outputArray);

        }
    }

    */

}