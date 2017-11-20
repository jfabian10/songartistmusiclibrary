# songartistmusiclibrary

Project done by Jesus Fabian(l3ogio22) & John Marshall(john9570)

You will write a memory management package for storing variable-length recordsin a large memory space.  For background on this project, view the modules on sequential fit memory man-agers in the OpenDSA class textbook. The records that you will store for this project are artist names and track names from a subset ofthe Million Song database.  This project will be the first in a series over the course of the semester that will gradually build up the necessary data structures for doing search and analysis on a largesong database.Your memory pool will consist of a large array of bytes.  You will use a doubly linked list tokeep track of the free blocks in the memory pool.  This list will be referred to as thefreeblock list.You will use thebest fitrule for selecting which free block to use for a memory request.  That is,the smallest free block in the linked list that is large enough to store the requested space will beused to service the request (if any such block exists).  If not all space of this block is needed, then the remaining space will make up a new free block and be returned to the free list.  If there is nofree block large enough to service the request, then you will grow the memory pool, as explainedbelow.Be sure to merge adjacent free blocks whenever a block is released.  To do the merge, whenevera block is released it will be necessary to search through the freeblock list, looking for blocks thatare adjacent to either the beginning or the end of the block being released.  Donotconsider thefirst and last memory positions of the memory pool to be adjacent.  That is, the memory pool itselfis not considered to be circular.Aside from the memory manager’s memory pool and freeblock list, the other major data struc-ture  for  your  project  will  be  two closed  hash  tables,  one  for  accessing  artist  names  and  theother for accessing song titles.  For information on hash tables, see the chapter on Hashing in theOpenDSA textbook.  You will use the second string hash function described in the book, and youwill use simple quadratic probing for your collision resolution method (thei’th probe step will bei2slots from the home slot).  The key difference from what the book describes is that your hash tablesmust beextensible.  That is, you will start with a hash table of a certain size (defined when theprogram starts).  If the hash table exceeds 50% full, then you will replace the array with anotherthat is twice the size, and rehash all of the records from the old array.  For example, say that thehash table has 100 slots.  Inserting 50 records is OK. When you try to insert the 51st record, youwould first re-hash all of the original 50 records into a table of 200 slots.  Likewise, if the hash tablestarted with 101 slots, you would also double it (to 202) just before inserting the 51st record.  Thehash table will actually store “handles” to the relevant data records that are currently stored inthe memory pool.  A handle is the value returned by the memory manager when a request is madeto insert a new record into the memory pool.  This handle is used to recover the record.
