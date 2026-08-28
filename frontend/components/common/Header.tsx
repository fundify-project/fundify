import Link from "next/link";

export default function Header() {
  return (
    <header className="sticky top-0 z-20 border-b border-line bg-ink/80 backdrop-blur-md">
      <div className="mx-auto flex h-16 max-w-5xl items-center px-6">
        <Link href="/" className="text-xl font-bold tracking-tight text-fg">
          <span className="text-mint">F</span>undify
        </Link>

        <nav className="ml-auto flex items-center gap-2">
          <Link
            href="/compare"
            className="rounded-lg px-3 py-2 text-sm text-fg-2 transition hover:bg-ink-3 hover:text-fg"
          >
            기업 비교
          </Link>
          <Link
            href="/login"
            className="rounded-lg border border-line px-3 py-2 text-sm text-fg transition hover:bg-ink-3"
          >
            로그인
          </Link>
        </nav>
      </div>
    </header>
  );
}
