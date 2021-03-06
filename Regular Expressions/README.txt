-----------------------------------------------------
=== Homework - 1 ===
-----------------------------------------------------
Name: Mahesh Shanbhag
Email: mrs160630@utdallas.edu
NetID: mrs160630

Requires Python 3.x

-----------------------------------------------------
== Contents ==
-----------------------------------------------------
1. USAGE: HOW TO RUN THE PROGRAM / INSTALLATION
2. PROGRAM DESCRIPTION
3. SAMPLE OUTPUT


-----------------------------------------------------
== USAGE: HOW TO RUN THE PROGRAM / INSTALLATION ==
-----------------------------------------------------
To run the program the user must navigate inside the "Homework1" folder where the python script is available.
There is also a folder 'Corpus', which contains the 'corpus.txt' file and 'TAGGED_DATE_NER_CORPUS.txt' file in which the dates have been tagged with the format [DATE <date and time>].

===== RUNNING VIA SCRIPT =====
There is one python script available ('CaptureDateAndTime.py') - With two modes.
1. CaptureDateAndTime.py (USAGE: "python3 CaptureDateAndTime.py <path to corpus>")

   a. Navigate into the 'Homework1' folder
   b. Copy and run the command 'python3 CaptureDateAndTime.py ./Corpus/corpus.txt' in the terminal.

2. CaptureDateAndTime.py (USAGE: "python3 CaptureDateAndTime.py <path to corpus> <Enable Intelligent Filter>")

   a. Navigate into the 'Homework1' folder
   b. Copy and run the command 'python3 CaptureDateAndTime.py ./Corpus/corpus.txt 0' or 'python3 CaptureDateAndTime.py ./Corpus/corpus.txt 1' in the terminal.

   0 - Same as the above variation; No intelligent filter is applied.
   1 - Intelligent filter is applied, year is extracted using contextual information like years preceded by words like In and Since.

NOTE: The general format is 'python3 CaptureDateAndTime.py <full path name to the corpus>'. Replace the '<full path name to the corpus>' with the actual path to the new corpus.


-----------------------------------------------------
== PROGRAM DESCRIPTION ==
-----------------------------------------------------
This program captures date and time from the 'Corpus.txt' file. Along with the corpus.txt file, a new file 'TAGGED_DATE_NER_CORPUS.txt' is created everytime the script is run, where the dates are tagged inline like [DATE <date>]

There are numerous ways by which we can represent date and time and the program tries to capture a few of them. Below is the description of the RE used in the program.

1. [Jj]an(?:uary)?|[Ff]eb(?:ruary)?|[Mm]ar(?:ch)?|[Aa]pr(?:il)?|[Mm]ay|[Jj]un(?:e)?|[Jj]ul(?:y)?|[Aa]ug(?:ust)?|[Ss]ept(?:ember)?|[Oo]ct(?:ober)?|[Nn]ov(?:ember)?|[Dd]ec(?:ember)? - This regular expression captures the Month in short and long form. eg. January or Jan or january or jan.
2. \d{1,2} - Captures the day of the month.
3. \d{4} - Captures the year.
4. \d{4}–\d{4} - Captures the year range.
5. Intelligent filter is available - year is extracted using contextual information such as years being preceded with words like 'In' and 'Since'.

These base regular expressions have been used with formatting to capture the DATE named entity. Once the formatting is complete, they are OR'ed into a single Regular Expression. 

-----------------------------------------------------
== SAMPLE OUTPUT ==
-----------------------------------------------------
[DATE 1972–1985]: The founding of Microsoft

Paul Allen and Bill Gates pose for the camera on [DATE October 19, 1981], in a sea of PCs after signing a pivotal contract with IBM
Childhood friends Paul Allen and Bill Gates sought to make a successful business utilizing their shared skills in computer programming. In [DATE 1972] they founded their first company, named Traf-O-Data, which sold a rudimentary computer to track and analyze automobile traffic data. While Gates enrolled at Harvard, Allen pursued a degree in computer science at Washington State University, though he later dropped out of school to work at Honeywell. The [DATE January 1975] issue of Popular Electronics featured Micro Instrumentation and Telemetry Systems's (MITS) Altair 8800 microcomputer, which inspired Allen to suggest that they could program a BASIC interpreter for the device. After a call from Gates claiming to have a working interpreter, MITS requested a demonstration. Since they didn't yet have one, Allen worked on a simulator for the Altair while Gates developed the interpreter. Although they developed the interpreter on a simulator and not the actual device, it worked flawlessly when they (in [DATE March 1975]) demonstrated the interpreter to MITS in Albuquerque, New Mexico. MITS agreed to distribute it, marketing it as Altair BASIC. Gates and Allen officially established Microsoft on [DATE April 4, 1975], with Gates as the CEO. The original name of "Micro-Soft" was suggested by Allen. In [DATE August 1977] the company formed an agreement with ASCII Magazine in Japan, resulting in its first international office, "ASCII Microsoft". Microsoft moved to a new home in Bellevue, Washington in [DATE January 1979].

Microsoft entered the operating system (OS) business in [DATE 1980] with its own version of Unix, called Xenix. However, it was MS-DOS that solidified the company's dominance. After negotiations with Digital Research failed, IBM awarded a contract to Microsoft in [DATE November 1980] to provide a version of the CP/M OS, which was set to be used in the upcoming IBM Personal Computer (IBM PC). For this deal, Microsoft purchased a CP/M clone called 86-DOS from Seattle Computer Products, which it branded as MS-DOS, though IBM rebranded it to PC DOS. Following the release of the IBM PC in [DATE August 1981], Microsoft retained ownership of MS-DOS. Since IBM had copyrighted the IBM PC BIOS, other companies had to reverse engineer it in order for non-IBM hardware to run as IBM PC compatibles, but no such restriction applied to the operating systems. Due to various factors, such as MS-DOS's available software selection, Microsoft eventually became the leading PC operating systems vendor. The company expanded into new markets with the release of the Microsoft Mouse in [DATE 1983], as well as with a publishing division named Microsoft Press. Paul Allen resigned from Microsoft in [DATE 1983] after developing Hodgkin's disease.

[DATE 1985–1994]: Windows and Office

Windows 1.0 was released on [DATE November 20, 1985] as the first version of the Microsoft Windows line

Timeline of Windows
Despite having begun jointly developing a new operating system, OS/2, with IBM in [DATE August 1985], Microsoft released Microsoft Windows, a graphical extension for MS-DOS, on November 20. Microsoft moved its headquarters to Redmond on [DATE February 26, 1986], and on March 13 went public, with the resulting rise in stock making an estimated four billionaires and 12,000 millionaires from Microsoft employees. Microsoft released its version of OS/2 to original equipment manufacturers (OEMs) on [DATE April 2, 1987]. In [DATE 1990], due to the partnership with IBM, the Federal Trade Commission set its eye on Microsoft for possible collusion, marking the beginning of over a decade of legal clashes with the U.S. government. Meanwhile, the company was at work on a 32-bit OS, Microsoft Windows NT, using ideas from OS/2. It shipped on [DATE July 21, 1993], with a new modular kernel and the Win32 application programming interface (API), making porting from 16-bit (MS-DOS-based) Windows easier. Once Microsoft informed IBM of NT, the OS/2 partnership deteriorated.

In [DATE 1990], Microsoft introduced its office suite, Microsoft Office. The suite bundled separate productivity applications, such as Microsoft Word and Microsoft Excel.[14]:301 On May 22, Microsoft launched Windows 3.0, featuring streamlined user interface graphics and improved protected mode capability for the Intel 386 processor. Both Office and Windows became dominant in their respective areas.

On [DATE July 27, 1994], the U.S. Department of Justice, Antitrust Division filed a Competitive Impact Statement that said, in part: "Beginning in [DATE 1988], and continuing until [DATE July 15, 1994], Microsoft induced many OEMs to execute anti-competitive "per processor" licenses. Under a per processor license, an OEM pays Microsoft a royalty for each computer it sells containing a particular microprocessor, whether the OEM sells the computer with a Microsoft operating system or a non-Microsoft operating system. In effect, the royalty payment to Microsoft when no Microsoft product is being used acts as a penalty, or tax, on the OEM's use of a competing PC operating system. Since [DATE 1988], Microsoft's use of per processor licenses has increased."

[DATE 1995–2007]: Foray into the Web, Windows 95, Windows XP, and Xbox

Microsoft released the first installment in the Xbox series of consoles in [DATE 2001]. The Xbox, graphically powerful compared to its rivals, featured a standard PC's 733 MHz Intel Pentium III processor.
Following Bill Gates's internal "Internet Tidal Wave memo" on [DATE May 26, 1995], Microsoft began to redefine its offerings and expand its product line into computer networking and the World Wide Web. The company released Windows 95 on [DATE August 24, 1995], featuring pre-emptive multitasking, a completely new user interface with a novel start button, and 32-bit compatibility; similar to NT, it provided the Win32 API. Windows 95 came bundled with the online service MSN (which was at first intended to be a competitor to the Internet), and (for OEMs) Internet Explorer, a web browser. Internet Explorer was not bundled with the retail Windows 95 boxes because the boxes were printed before the team finished the web browser, and instead was included in the Windows 95 Plus! pack. Branching out into new markets in [DATE 1996], Microsoft and General Electric's NBC unit created a new 24/7 cable news channel, MSNBC. Microsoft created Windows CE 1.0, a new OS designed for devices with low memory and other constraints, such as personal digital assistants.In [DATE October 1997], the Justice Department filed a motion in the Federal District Court, stating that Microsoft violated an agreement signed in [DATE 1994] and asked the court to stop the bundling of Internet Explorer with Windows.


In [DATE 1996], Microsoft released Windows CE, a version of the operating system meant for personal digital assistants and other tiny computers.
On [DATE January 13, 2000], Bill Gates handed over the CEO position to Steve Ballmer, an old college friend of Gates and employee of the company since [DATE 1980], while creating a new position for himself as Chief Software Architect. Various companies including Microsoft formed the Trusted Computing Platform Alliance in [DATE October 1999] to (among other things) increase security and protect intellectual property through identifying changes in hardware and software. Critics decried the alliance as a way to enforce indiscriminate restrictions over how consumers use software, and over how computers behave, and as a form of digital rights management: for example the scenario where a computer is not only secured for its owner, but also secured against its owner as well. On [DATE April 3, 2000], a judgment was handed down in the case of United States v. Microsoft, calling the company an "abusive monopoly." Microsoft later settled with the U.S. Department of Justice in [DATE 2004]. On [DATE October 25, 2001], Microsoft released Windows XP, unifying the mainstream and NT lines of OS under the NT codebase. The company released the Xbox later that year, entering the game console market dominated by Sony and Nintendo. In [DATE March 2004] the European Union brought antitrust legal action against the company, citing it abused its dominance with the Windows OS, resulting in a judgment of €497 million ($613 million) and requiring Microsoft to produce new versions of Windows XP without Windows Media Player: Windows XP Home Edition N and Windows XP Professional N. In [DATE November 2005], the Xbox 360 was released.

[DATE 2007–2011]: Microsoft Azure, Windows Vista, Windows 7, and Microsoft Stores

CEO Steve Ballmer at the MIX event in [DATE 2008]. In an interview about his management style in [DATE 2005], he mentioned that his first priority was to get the people he delegates to in order. Ballmer also emphasized the need to continue pursuing new technologies even if initial attempts fail, citing the original attempts with Windows as an example.
Released in [DATE January 2007], the next version of Windows, Vista, focused on features, security and a redesigned user interface dubbed Aero. Microsoft Office 2007, released at the same time, featured a "Ribbon" user interface which was a significant departure from its predecessors. Relatively strong sales of both products helped to produce a record profit in [DATE 2007]. The European Union imposed another fine of €899 million ($1.4 billion) for Microsoft's lack of compliance with the [DATE March 2004] judgment on [DATE February 27, 2008], saying that the company charged rivals unreasonable prices for key information about its workgroup and backoffice servers. Microsoft stated that it was in compliance and that "these fines are about the past issues that have been resolved". 2007 also saw the creation of a multi-core unit at Microsoft, following the steps of server companies such as Sun and IBM.

Gates retired from his role as Chief Software Architect on [DATE June 27, 2008], a decision announced in [DATE June 2006], while retaining other positions related to the company in addition to being an advisor for the company on key projects. Azure Services Platform, the company's entry into the cloud computing market for Windows, launched on [DATE October 27, 2008]. On [DATE February 12, 2009], Microsoft announced its intent to open a chain of Microsoft-branded retail stores, and on [DATE October 22, 2009] the first retail Microsoft Store opened in Scottsdale, Arizona; the same day Windows 7 was officially released to the public. Windows 7's focus was on refining Vista with ease of use features and performance enhancements, rather than a large reworking of Windows.

As the smartphone industry boomed in [DATE 2007], Microsoft had struggled to keep up with its rivals Apple and Google in providing a modern smartphone operating system. As a result, in [DATE 2010] Microsoft revamped their aging flagship mobile operating system, Windows Mobile, replacing it with the new Windows Phone OS. Microsoft implemented a new strategy for the software industry that had them working more closely with smartphone manufacturers, such as Nokia, and providing a consistent user experience across all smartphones using the Windows Phone OS. It used a new user interface design language, codenamed "Metro", which prominently used simple shapes, typography and iconography, utilizing the concept of minimalism. Microsoft is a founding member of the Open Networking Foundation started on [DATE March 23, 2011]. Fellow founders were Google, HP Networking, Yahoo, Verizon, Deutsche Telekom and 17 other companies. This nonprofit organization is focused on providing support for a new cloud computing initiative called Software-Defined Networking. The initiative is meant to speed innovation through simple software changes in telecommunications networks, wireless networks, data centers and other networking areas.

Input Corpus: ./Corpus/corpus.txt
Total captured Date and Time Count: 59

Captured date and time:
(1.) 1972
(2.) 1972–1985
(3.) 1980
(4.) 1980
(5.) 1983
(6.) 1983
(7.) 1985–1994
(8.) 1988
(9.) 1988
(10.) 1990
(11.) 1990
(12.) 1994
(13.) 1995–2007
(14.) 1996
(15.) 1996
(16.) 2001
(17.) 2004
(18.) 2005
(19.) 2007
(20.) 2007
(21.) 2007
(22.) 2007
(23.) 2007–2011
(24.) 2008
(25.) 2010
(26.) 8800
(27.) April 2, 1987
(28.) April 3, 2000
(29.) April 4, 1975
(30.) August 1977
(31.) August 1981
(32.) August 1985
(33.) August 24, 1995
(34.) February 12, 2009
(35.) February 26, 1986
(36.) February 27, 2008
(37.) January 13, 2000
(38.) January 1975
(39.) January 1979
(40.) January 2007
(41.) July 15, 1994
(42.) July 21, 1993
(43.) July 27, 1994
(44.) June 2006
(45.) June 27, 2008
(46.) March 1975
(47.) March 2004
(48.) March 2004
(49.) March 23, 2011
(50.) May 26, 1995
(51.) November 1980
(52.) November 20, 1985
(53.) November 2005
(54.) October 19, 1981
(55.) October 1997
(56.) October 1999
(57.) October 22, 2009
(58.) October 25, 2001
(59.) October 27, 2008