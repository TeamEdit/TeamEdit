package application.model;

import java.io.File;

import org.blinkenlights.jid3.*;
import org.blinkenlights.jid3.v1.*;
import org.blinkenlights.jid3.v2.*;

public class metaEdit {
	
	public static void main(String []args) {
		metaEdit me = new metaEdit();
		Song s = new Song("Kero Kero Bonito", "Waiting", "Bonito Generation", 2020);
		String filename = "song.mp3";
		try {
			me.editFile(filename, s);
		} catch (ID3Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void editFile(String iFile, Song newData) throws ID3Exception {
		// the file we are going to modify
        File oSourceFile = new File(iFile);

        // create an MP3File object representing our chosen file
        MediaFile oMediaFile = new MP3File(oSourceFile);

        // create a v1.0 tag object, and set some values
        ID3V1_0Tag oID3V1_0Tag = new ID3V1_0Tag();
        
        String album = newData.getAlbum();
        album =	album.substring(0, Math.min(album.length(), 30));

        String artist = newData.getArtist();
        artist = artist.substring(0, Math.min(artist.length(), 30));

        String title = newData.getTitle();
        title =	title.substring(0, Math.min(title.length(), 30));

        int year  		= newData.getYear();
        String yearStr 	= String.valueOf(year);
        yearStr 		= yearStr.substring(0, Math.min(yearStr.length(), 30));

        oID3V1_0Tag.setAlbum(album);
        oID3V1_0Tag.setArtist(artist);
        //Genre in v1.0 is a disaster. It's a single bit character that corresponds with a 255 bit list
        //Maybe could be implemented with a lot of additional work
        //oID3V1_0Tag.setGenre(ID3V1Tag.Genre.<SAMPLE>);
        oID3V1_0Tag.setTitle(title);
        oID3V1_0Tag.setYear(yearStr);
       
        // set this v1.0 tag in the media file object
        oMediaFile.setID3Tag(oID3V1_0Tag);
       
        // create a v2.3.0 tag object, and set some frames
        ID3V2_3_0Tag oID3V2_3_0Tag = new ID3V2_3_0Tag();
        oID3V2_3_0Tag.setAlbum(album);  // sets TALB frame
        oID3V2_3_0Tag.setArtist(artist);  // sets TPE1 frame
        oID3V2_3_0Tag.setTitle(title);  // sets TIT2 frame
        oID3V2_3_0Tag.setYear(year);  // sets TYER frame
        
//        TPE1TextInformationID3V2Frame oTPE1 = new TPE1TextInformationID3V2Frame(artist);
//        oID3V2_3_0Tag.setTPE1TextInformationFrame(oTPE1);
//        //TRCKTextInformationID3V2Frame oTRCK = new TRCKTextInformationID3V2Frame(3, 9);
//        //oID3V2_3_0Tag.setTRCKTextInformationFrame(oTRCK);
//        TIT2TextInformationID3V2Frame oTIT2 = new TIT2TextInformationID3V2Frame(title);
//        oID3V2_3_0Tag.setTIT2TextInformationFrame(oTIT2);
//        
//        TIT2TextInformationID3V2Frame oTIT3 = new TIT2TextInformationID3V2Frame(year);
//        oID3V2_3_0Tag.setTIT2TextInformationFrame(oTIT3);
//        
//        TIT2TextInformationID3V2Frame oTIT4 = new TIT2TextInformationID3V2Frame(album);
//        oID3V2_3_0Tag.setTIT2TextInformationFrame(oTIT4);
//        // set this v2.3.0 tag in the media file object
        oMediaFile.setID3Tag(oID3V2_3_0Tag);
       
        // update the actual file to reflect the current state of our object 
        oMediaFile.sync();
		
	}

	public Song readFile(String iFile) throws ID3Exception {
		// the file we are going to read
		Song readSong = null;
        File oSourceFile = new File(iFile);

        // create an MP3File object representing our chosen file
        MediaFile oMediaFile = new MP3File(oSourceFile);

        // any tags read from the file are returned, in an array, in an order which you should not assume
        ID3Tag[] aoID3Tag = oMediaFile.getTags();
        // let's loop through and see what we've got
        // (NOTE:  we could also use getID3V1Tag() or getID3V2Tag() methods, if we specifically want one or the other)
        for (int i=0; i < aoID3Tag.length; i++)
        {
            // check to see if we read a v1.0 tag, or a v2.3.0 tag
            if (aoID3Tag[i] instanceof ID3V1_0Tag)
            {
                ID3V1_0Tag oID3V1_0Tag = (ID3V1_0Tag)aoID3Tag[i];
                // does this tag happen to contain a title?
                if (oID3V1_0Tag.getTitle() != null)
                {
                	readSong.setTitle(oID3V1_0Tag.getTitle());
                    System.out.println("Title (v1) = " + oID3V1_0Tag.getTitle());
                }
                else if (oID3V1_0Tag.getYear() != null) {
                	readSong.setYear(Integer.parseInt(oID3V1_0Tag.getYear()));
                }
                else if (oID3V1_0Tag.getArtist() != null) {
                	readSong.setArtist(oID3V1_0Tag.getArtist());
                }
                else if (oID3V1_0Tag.getAlbum() != null) {
                	readSong.setAlbum(oID3V1_0Tag.getAlbum());
                }
                // etc.
            }
            else if (aoID3Tag[i] instanceof ID3V2_3_0Tag)
            {
                ID3V2_3_0Tag oID3V2_3_0Tag = (ID3V2_3_0Tag)aoID3Tag[i];
                // check if this v2.3.0 frame contains a title, using the actual frame name
                if (oID3V2_3_0Tag.getTIT2TextInformationFrame() != null) {
                	readSong.setTitle(oID3V2_3_0Tag.getTIT2TextInformationFrame().getTitle());
                    System.out.println("Title = (v2.3) " + oID3V2_3_0Tag.getTIT2TextInformationFrame().getTitle());
                }
                
                // but check using the convenience method if it has a year set (either way works)
                try
                {
                	readSong.setYear(oID3V2_3_0Tag.getYear());
                    System.out.println("Year = " + oID3V2_3_0Tag.getYear());  // reads TYER frame
                }
                catch (ID3Exception e)
                {
                    // error getting year.. if one wasn't set
                    System.out.println("Could get read year from tag: " + e.toString());
                }
                // etc.
            }
        }
		return readSong;
	}
	
	
}
