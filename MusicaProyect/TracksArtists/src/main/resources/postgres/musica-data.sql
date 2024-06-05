insert into artist(id,name)
values (0,'Lana Del Rey'),
        (1,'the offspring'),
        (2,'Mana'),
        (3,'Brainstory'),
        (4,'Tiesto'),
        (5,'Slipnot');

insert into track(duration,id,media_type,price,issue_date,album,title)
values (1000,0,2,5.00,'2024-02-05T12:59:11.332','Pink Friday','Anaconda'),
      (500,1,2,5.00,'2005-01-15T12:59:11.332','Bad habit','Bad Habit'),
        (630,2,0,5.00,'2010-02-25T12:59:11.332','Album1','Mariposa Traicionera'),
    (524,3,2,5.00,'2024-05-08T12:59:11.332','Good Vibes','Beautyful Beaty'),
    (625,4,1,5.00,'2003-04-05T12:59:11.332','Adagio Strings','Adagio'),
    (440,5,0,5.00,'2008-08-16T12:59:11.332','Menace','Pain');

insert into artist_track(artist_id,track_id)
values(0,0),
    (1,1),
      (2,2),
      (3,3),
      (4,4),
    (5,5),
(4,5);
