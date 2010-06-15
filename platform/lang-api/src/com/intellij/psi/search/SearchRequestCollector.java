package com.intellij.psi.search;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.util.Processor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
* @author peter
*/
public class SearchRequestCollector {
  private final List<PsiSearchRequest> myRequests = new ArrayList<PsiSearchRequest>();
  private final List<Processor<Processor<PsiReference>>> myCustomSearchActions = new ArrayList<Processor<Processor<PsiReference>>>();

  public void searchWord(@NotNull String word, @NotNull SearchScope searchScope, boolean caseSensitive, @NotNull PsiElement searchTarget) {
    final short searchContext = UsageSearchContext.IN_CODE | UsageSearchContext.IN_FOREIGN_LANGUAGES | UsageSearchContext.IN_COMMENTS;
    searchWord(word, searchScope, searchContext, caseSensitive, searchTarget);
  }

  public void searchWord(@NotNull String word, @NotNull SearchScope searchScope, short searchContext, boolean caseSensitive, @NotNull PsiElement searchTarget) {
    searchWord(word, searchScope, searchContext, caseSensitive, new SingleTargetRequestResultProcessor(searchTarget));
  }

  public void searchWord(@NotNull String word, @NotNull SearchScope searchScope, short searchContext, boolean caseSensitive, @NotNull RequestResultProcessor processor) {
    myRequests.add(new PsiSearchRequest(searchScope, word, searchContext, caseSensitive, processor));
  }

  public void searchCustom(Processor<Processor<PsiReference>> searchAction) {
    myCustomSearchActions.add(searchAction);
  }

  public List<PsiSearchRequest> getSearchRequests() {
    return myRequests;
  }

  public List<Processor<Processor<PsiReference>>> getCustomSearchActions() {
    return myCustomSearchActions;
  }
}
